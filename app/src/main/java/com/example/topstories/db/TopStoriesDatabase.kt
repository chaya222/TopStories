package com.example.topstories.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.topstories.db.dao.TopStoriesDao
import com.example.topstories.db.entity.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
abstract class TopStoriesDatabase : RoomDatabase() {
    companion object{
        const val databaseName = "article_db"
    }
    abstract fun topStoriesDao(): TopStoriesDao
}