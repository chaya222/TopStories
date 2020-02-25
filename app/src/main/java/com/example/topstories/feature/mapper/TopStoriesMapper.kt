package com.example.topstories.feature.mapper

import com.example.topstories.db.entity.ArticleEntity
import com.example.topstories.feature.data.ArticleItem
import com.example.topstories.feature.network.TopStoriesResult

fun getTopStoriesItemFromResponse(item: TopStoriesResult): ArticleItem {
    return ArticleItem().apply {
        title = item.title
        section = item.section
        subsection = item.subsection
    }
}


fun getTopStoriesEntityFromResponse(item: TopStoriesResult): ArticleEntity {
     var multiMdUrl = ""
    item.multimediaList.let {
        if(it.isNotEmpty()){
            multiMdUrl=it[0].url
        }
    }
    return ArticleEntity(
        title = item.title,
        section = item.section,
        subsection = item.subsection,
        abstracts = item.abstract,
        byline = item.byline,
        published_date = item.published_date,
        articleUrl = item.articleUrl,
        multimediaUrl = multiMdUrl


    )
}