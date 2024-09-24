package com.example.newsapp.news.network.dto

data class Response(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)