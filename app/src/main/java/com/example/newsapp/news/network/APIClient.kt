package com.example.newsapp.news.network

import com.example.newsapp.news.network.dto.Article
import com.example.newsapp.news.network.dto.Response

object APIClient : RemoteDataSource {
    private const val API_KEY = "dcce2871da7641b887c73f17d0e4d5c8"

    override suspend fun getNews(): Response {
        return RetrofitHelper.apiService.getNews(apiKey = API_KEY)
    }

    override suspend fun getNewsByCategory(category: String): Response {
        return RetrofitHelper.apiService.getNewsByCategory(category, API_KEY)
    }

    override suspend fun getNewsByQuery(query: String): Response {
        return RetrofitHelper.apiService.getNewsByQuery(query, API_KEY)
    }
}