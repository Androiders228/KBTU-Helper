package com.example.kbtu_helper.data.api

data class NewsResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: List<ApiArticle> = emptyList()
)

data class ApiArticle(
    val source: SourceDto?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)

data class SourceDto(
    val id: String?,
    val name: String?
)
