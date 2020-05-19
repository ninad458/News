package com.enigma.newsfeed

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                        val body = response.body()
                        Log.d("zzzzz", body.toString())
                        body?.articles?.forEach {
                            Log.d("zzzzzzz", it.toString())
                        }

                    }
                })
            }
        }).start()
    }
}
