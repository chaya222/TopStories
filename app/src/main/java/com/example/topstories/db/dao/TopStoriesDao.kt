package com.example.topstories.db.dao

import androidx.room.*
import com.example.topstories.db.entity.ArticleEntity
import io.reactivex.Flowable

@Dao
interface TopStoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articles: List<ArticleEntity>)

    @Transaction
    fun insertFetchedArticle(entities: List<ArticleEntity>) {
        insertArticles(entities)
    }

    @Query("SELECT * FROM articles_table where section = :section")
    fun getAllArticle(section : String): Flowable<List<ArticleEntity>>

}