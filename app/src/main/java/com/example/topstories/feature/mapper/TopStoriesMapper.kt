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
    return ArticleEntity(
        title = item.title,
        section = item.section,
        subsection = item.subsection
    )
}