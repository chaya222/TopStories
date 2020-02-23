package com.example.topstories.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "articles_table")
data class ArticleEntity(
    @PrimaryKey
    val title : String = "",
    val section : String = "",
    val subsection : String = ""
)