package com.enigma.newsfeed

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNews(news: List<NewsEntity>)

    @Query("SELECT * FROM news")
    suspend fun getAllNews(): List<NewsEntity>

}