package com.enigma.newsfeed

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNews(news: List<NewsEntity>)

    @Query("SELECT * FROM news")
    fun getAllNews(): List<NewsEntity>

}