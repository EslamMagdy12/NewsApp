package com.example.newsapp.news.network

import com.example.newsapp.news.network.dto.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=dcce2871da7641b887c73f17d0e4d5c8

interface APIService {
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String
    ): Response

    @GET("v2/top-headlines")
    suspend fun getNewsByCategory(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): Response

    @GET("v2/everything")
    suspend fun getNewsByQuery(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): Response
}
