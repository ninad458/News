package com.enigma.newsfeed

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface Api {

    companion object {
        fun getApi(): Api {

            val logging = HttpLoggingInterceptor()
// set your desired log level
// set your desired log level
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
// add your other interceptors …

// add logging as last interceptor
// add your other interceptors …

// add logging as last interceptor
            httpClient.addInterceptor(logging)
            val retrofit = Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
            return retrofit.create(Api::class.java)
        }
    }

    @GET("top-headlines")
    fun getHeadlines(
        @Header("x-api-key") apiKey: String,
        @Query("country") country: String
    ): Call<NewsResponse>
}