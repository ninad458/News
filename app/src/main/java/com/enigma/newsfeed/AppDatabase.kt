package com.enigma.newsfeed

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NewsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}

val Context.appDB
    get() = Room.databaseBuilder(
        this,
        AppDatabase::class.java, "database-name"
    ).build()