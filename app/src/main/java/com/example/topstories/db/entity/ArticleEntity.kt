package com.example.topstories.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "articles_table")
data class ArticleEntity(
    @PrimaryKey
    var title : String = "",
    var section : String = "",
    var subsection : String = "",
    var abstract : String = "",
    var byline : String = "",
    var published_date : String = "",
    var articleUrl : String = "",
    var multimediaUrl : String = ""
)