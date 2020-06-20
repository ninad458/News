package com.enigma.newsfeed

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    companion object {
        const val PAGE_SIZE: Int = 20
    }

    private val newsAdapter = NewsAdapter()

    private val scope = MainScope()

    private val newsDB by lazy { appDB.newsDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadList()
        loadNews()
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    private fun loadNews(pageNo: Int = 1) {
        scope.launch {
            if (pageNo != 1) newsAdapter.loading = true
            list.clearOnScrollListeners()
            try {
                val news = Api.getApi().getHeadlines(
                    BuildConfig.API_KEY,
                    "in", pageNo, PAGE_SIZE
                )
                newsAdapter.loading = false
                val articles = news.articles
                if (articles.isEmpty()) return@launch

                newsDB.addNews(articles.map { (source, author, title, description, url, urlToImage, publishedAt, content) ->
                    NewsEntity(
                        source.name,
                        author,
                        title,
                        description,
                        url,
                        urlToImage,
                        publishedAt,
                        content
                    )
                })

                println("zzzzzzzz ${newsDB.getAllNews()}")

                if (newsAdapter.newsCount == 0) newsAdapter.setNews(articles)
                else newsAdapter.addNews(articles)
                val newsCount = newsAdapter.newsCount
                list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(
                        recyclerView: RecyclerView,
                        dx: Int,
                        dy: Int
                    ) {
                        super.onScrolled(recyclerView, dx, dy)
                        (recyclerView.layoutManager as? LinearLayoutManager)?.apply {
                            val lastVisible = findLastVisibleItemPosition()
                            val itemCount = itemCount

                            if (lastVisible + 2 > itemCount) {
                                loadNews((ceil(newsCount.toDouble() / PAGE_SIZE) + 1).toInt())
                            }
                        }
                    }
                })

            } catch (e: IOException) {
                Log.e("zzzzzz", e.message ?: "Something went wrong")
            }
        }
    }

    private fun loadList() {
        list.adapter = newsAdapter
        list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
