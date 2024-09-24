package com.example.newsapp.news.repo

import androidx.lifecycle.LiveData
import com.example.newsapp.news.network.RemoteDataSource
import com.example.newsapp.news.network.db.LocalDataSource
import com.example.newsapp.news.network.dto.Article
import com.example.newsapp.news.network.dto.Response

class NewsRepositoryImplementation(private val localDataSource: LocalDataSource, private val remoteDataSource: RemoteDataSource) :
    NewsRepository {
    override suspend fun insert(article: Article) {
        localDataSource.insert(article)
    }

    override suspend fun getArticles(): List<Article> {
        return localDataSource.getArticles()
    }

    override suspend fun delete(article: Article) {
        localDataSource.delete(article)
    }

    override fun isFavorite(article: Article): LiveData<Boolean> {
        return localDataSource.isFavorite(article)
    }

    override suspend fun getNews() : Response {
        return remoteDataSource.getNews()
    }

    override suspend fun getNewsByCategory(category: String) : Response {
        return remoteDataSource.getNewsByCategory(category)
    }

    override suspend fun getNewsByQuery(query: String) : Response {
        return remoteDataSource.getNewsByQuery(query)
    }
}
