package com.example.newsapp.news.repo

import androidx.lifecycle.LiveData
import com.example.newsapp.news.network.dto.Article
import com.example.newsapp.news.network.dto.Response

interface NewsRepository {
    // Local
    suspend fun insert(article: Article)
    suspend fun getArticles(): List<Article>
    suspend fun delete(article: Article)
    fun isFavorite(article: Article): LiveData<Boolean>

    // Remote
    suspend fun getNews(): Response
    suspend fun getNewsByCategory(category: String): Response
    suspend fun getNewsByQuery(query: String): Response
}