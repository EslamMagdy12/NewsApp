package com.example.newsapp.news.network

import com.example.newsapp.news.network.dto.Response

// https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=dcce2871da7641b887c73f17d0e4d5c8
interface RemoteDataSource {
    suspend fun getNews(): Response
    suspend fun getNewsByCategory(category: String): Response
    suspend fun getNewsByQuery(query: String): Response
}