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
            val apiArticles = response.articles ?: emptyList()

            if (apiArticles.isNotEmpty()) {
                val articles = apiArticles.mapNotNull { api ->
                    if (api.url.isNullOrBlank()) null
                    else NewsArticle(
                        url = api.url,
                        title = api.title,
                        description = api.description,
                        urlToImage = api.urlToImage
                    )
                }
                dao.clearTable()
                dao.insertArticles(articles)
                return@withContext articles
            }

            dao.getArticles()
        } catch (e: Exception) {
            android.util.Log.e("NewsRepository", "Error fetching news", e)
            dao.getArticles()
        }
    }
}
