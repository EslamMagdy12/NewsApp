package com.example.newsapp.news.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.news.network.dto.Article
import com.example.newsapp.news.network.dto.Response
import com.example.newsapp.news.repo.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

//    private lateinit var  repository: ProductsRepository

//    fun setRepository(repository: ProductsRepository){
//        this.repository = repository
//    }

    private val _news = MutableLiveData<Response>()
    val news: LiveData<Response>
        get() = _news

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>>
        get() = _articles


//    private val _newsByCategory = MutableLiveData<Response>()
//    val newsByCategory: LiveData<Response>
//        get() = _newsByCategory
//
//    private val _newsByQuery = MutableLiveData<Response>()
//    val newsByQuery: LiveData<Response>
//        get() = _newsByQuery

    fun getNews() {
        viewModelScope.launch {
            _news.value = repository.getNews()
        }
    }

    fun getNewsByCategory(category: String) {
        viewModelScope.launch {
            _news.value = repository.getNewsByCategory(category)
        }
    }

    fun getNewsByQuery(query: String) {
        viewModelScope.launch {
            _news.value = repository.getNewsByQuery(query)
        }
    }

    fun insert(article: Article) {
        viewModelScope.launch {
            repository.insert(article)
        }
    }

    fun getArticles() {
        viewModelScope.launch {
            _articles.value = repository.getArticles()
        }
    }

    fun delete(article: Article) {
        viewModelScope.launch {
            repository.delete(article)
        }
    }

    fun isFavorite(article: Article): LiveData<Boolean> {
        return repository.isFavorite(article)
    }
}