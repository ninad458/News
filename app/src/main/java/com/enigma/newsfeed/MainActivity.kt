package com.enigma.newsfeed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object {
        const val PAGE_SIZE: Int = 20
    }

    private val newsAdapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadList()
        loadNews()
    }

    private fun loadNews(pageNo: Int = 1) {
        list.clearOnScrollListeners()
        val call = Api.getApi().getHeadlines(
            BuildConfig.API_KEY,
            "in", pageNo, PAGE_SIZE
        )
        call.enqueue(object : Callback<NewsResponse> {

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                val body = response.body() ?: return
                val articles = body.articles
                if (articles.isEmpty()) return

                if (newsAdapter.newsCount == 0) newsAdapter.setNews(articles)
                else newsAdapter.addNews(articles)
                val newsCount = newsAdapter.newsCount
                list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        (recyclerView.layoutManager as? LinearLayoutManager)?.apply {
                            val lastVisible = findLastVisibleItemPosition()
                            val itemCount = itemCount

                            if (lastVisible + 2 > itemCount) {
                                loadNews((newsCount / PAGE_SIZE) + 1)
                            }
                        }
                    }
                })
            }
        })
    }

    private fun loadList() {
        list.adapter = newsAdapter
        list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
