package com.example.topstories.feature.data

data class ArticleItem(
    var title : String = "",
    var section : String = "",
    var subsection : String = "",
    var abstract : String = "",
    var byline : String = "",
    var published_date : String = "",
    var articleUrl : String = "",
    var multimediaUrl : String = ""
)