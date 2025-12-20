package com.example.kbtu_helper.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getEducationNews(
        @Query("q") query: String = "education",
        @Query("apiKey") apiKey: String,
        @Query("language") language: String = "en",
        @Query("sortBy") sortBy: String = "publishedAt"
    ): NewsResponse
}