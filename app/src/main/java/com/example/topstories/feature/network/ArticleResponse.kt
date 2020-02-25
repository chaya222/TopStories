package com.example.topstories.feature.network

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("results")
    var articleList: List<TopStoriesResult> = ArrayList()
)

data class TopStoriesResult(
    @SerializedName("title")
    var title: String = "",
    @SerializedName("section")
    var section: String = "",
    @SerializedName("subsection")
    var subsection: String = "",
    @SerializedName("abstract")
    var abstract: String = "",
    @SerializedName("byline")
    var byline: String = "",
    @SerializedName("published_date")
    var published_date: String = "",
    @SerializedName("short_url")
    var articleUrl: String = "",
    @SerializedName("multimedia")
    var multimediaList : List<Multimedia> = ArrayList()

)

data class Multimedia(
    @SerializedName("url")
    var url : String = ""
)