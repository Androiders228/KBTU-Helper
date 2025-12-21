package com.example.kbtu_helper.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class NewsArticle(
    @PrimaryKey val url: String,
    val title: String?,
    val description: String?,
    val urlToImage: String?
)
