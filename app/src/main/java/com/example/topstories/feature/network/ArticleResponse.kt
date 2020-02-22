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
    var subsection: String = ""
)