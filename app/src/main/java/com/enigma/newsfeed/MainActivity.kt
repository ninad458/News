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
                val call = Api.getApi().getHeadlines()
                call.enqueue(object : Callback<Any> {
                    override fun onFailure(call: Call<Any>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        Log.d("zzzzz", response.toString())
                    }

                })
            }
        }).start()
    }
}
