package com.example.topstories.db.dao

import androidx.room.*
import com.example.topstories.db.entity.ArticleEntity
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface TopStoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articles: List<ArticleEntity>)

    @Transaction
    fun insertFetchedArticle(entities: List<ArticleEntity>) {
        insertArticles(entities)
    }

    @Query("SELECT * FROM articles_table")
    fun getAllArticle(): Flowable<List<ArticleEntity>>

    @Query("SELECT * FROM articles_table where title= :name ")
    fun getArticleByTitle(name : String): Observable<ArticleEntity>

    @Query("SELECT * FROM articles_table")
    fun getAllArticleWdoutObserve(): List<ArticleEntity>

}