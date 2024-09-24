package com.example.newsapp.news.network.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.news.network.dto.Article

@Dao
interface ArticleDAO {
    @Insert
    suspend fun insert(article: Article)

    @Query("SELECT * FROM articles")
    suspend fun getArticles(): List<Article>

    @Query("DELETE FROM articles WHERE url = :url")
    suspend fun delete(url: String)

//    @Delete
//    suspend fun delete(article: Article)

    @Query("SELECT EXISTS (SELECT 1 FROM articles WHERE url = :url)")
    fun isFavorite(url: String): LiveData<Boolean>


}