package com.example.kbtu_helper.data

import com.example.kbtu_helper.data.api.RetrofitInstance
import com.example.kbtu_helper.data.local.NewsArticle
import com.example.kbtu_helper.data.local.NewsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class NewsRepository(
    private val apiKey: String,
    private val dao: NewsDao
) {


    suspend fun getNews(): List<NewsArticle> = withContext(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.getEducationNews(apiKey = apiKey)
            val apiArticles = response.articles

            val articles = apiArticles.mapNotNull { api ->
                val url = api.url
                if (url.isNullOrBlank()) null
                else NewsArticle(
                    url = url,
                    title = api.title,
                    description = api.description,
                    urlToImage = api.urlToImage
                )
            }

            dao.clearTable()
            if (articles.isNotEmpty()) {
                dao.insertArticles(articles)
            }

            if (articles.isNotEmpty()) articles else dao.getArticles()
        } catch (e: Exception) {
            dao.getArticles()
        }
    }
}
