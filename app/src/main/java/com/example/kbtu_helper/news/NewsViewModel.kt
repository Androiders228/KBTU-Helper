package com.example.kbtu_helper.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kbtu_helper.data.NewsRepository
import com.example.kbtu_helper.data.local.NewsArticle
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _news = MutableLiveData<List<NewsArticle>>(emptyList())
    val news: LiveData<List<NewsArticle>> = _news

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    fun loadNews() {
        _loading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val articles = repository.getNews()
                _news.value = articles
            } catch (t: Throwable) {
                _error.value = t.message ?: "Unknown error"
            } finally {
                _loading.value = false
            }
        }
    }
}
