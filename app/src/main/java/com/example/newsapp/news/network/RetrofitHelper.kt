package com.example.newsapp.news.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=dcce2871da7641b887c73f17d0e4d5c8

object RetrofitHelper {

    private val gson = GsonBuilder()
        .serializeNulls()
        .create()

    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(OkHttpClient.Builder().addInterceptor(logging).build())
        .build()

//    val service = retrofit.create(SimpleService::class.java)

    val apiService: APIService by lazy {
        retrofit.create(APIService::class.java)
    }

}