package com.example.kbtu_helper.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getEducationNews(
        @Query("category") category: String = "education",
        @Query("apiKey") apiKey: String,
        @Query("country") country: String = "us"
    ): NewsResponse
}
