package com.enigma.newsfeed

import androidx.room.Entity
import java.util.*

@Entity(tableName = "news", primaryKeys = ["sourceName", "title"])
data class NewsEntity(
    val sourceName: String,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String?
)