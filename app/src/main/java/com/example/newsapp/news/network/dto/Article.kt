package com.example.newsapp.news.network.dto

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val author: String?,
    val content: String,
    val description: String?,
    val publishedAt: String,
    val source: Source,
    val title: String?,
    val url: String,
    val urlToImage: String?,
) : Serializable {

}