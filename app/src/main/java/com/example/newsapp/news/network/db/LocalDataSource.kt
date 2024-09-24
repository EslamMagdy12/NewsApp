package com.example.newsapp.news.network.db

import androidx.lifecycle.LiveData
import com.example.newsapp.news.network.dto.Article

interface LocalDataSource {

    suspend fun insert(article: Article)

    suspend fun getArticles(): List<Article>

    suspend fun delete(article: Article)

    fun isFavorite(article: Article): LiveData<Boolean>

}