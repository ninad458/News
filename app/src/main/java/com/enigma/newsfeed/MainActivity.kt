package com.enigma.newsfeed

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val newsAdapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list.adapter = newsAdapter

        Thread(object : Runnable {
            override fun run() {
                val call = Api.getApi().getHeadlines(BuildConfig.API_KEY, "in")
                call.enqueue(object : Callback<NewsResponse> {

                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    }

                    override fun onResponse(
                        call: Call<NewsResponse>,
                        response: Response<NewsResponse>
                    ) {
                        val body = response.body() ?: return
                        Log.d("zzzzz", body.toString())
                        body.articles.forEach {
                            Log.d("zzzzzzz", it.toString())
                        }
                        runOnUiThread {
                            newsAdapter.setNews(body.articles)
                        }
                    }
                })
            }
        }).start()
    }
}
