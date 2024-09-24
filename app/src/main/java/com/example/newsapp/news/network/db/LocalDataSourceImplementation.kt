package com.example.newsapp.news.network.db

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.newsapp.news.network.dto.Article

class LocalDataSourceImplementation(context: Context) : LocalDataSource {
    private var dao: ArticleDAO

    init {
        val db = ArticleDatabase.getInstance(context)
        dao = db.articleDao()
    }

    override suspend fun insert(article: Article) {
        dao.insert(article)
    }

    override suspend fun getArticles(): List<Article> {
        return dao.getArticles()
    }

    override suspend fun delete(article: Article) {
        dao.delete(article.url)
    }

    override fun isFavorite(article: Article): LiveData<Boolean> {
        return dao.isFavorite(article.url)
    }

}