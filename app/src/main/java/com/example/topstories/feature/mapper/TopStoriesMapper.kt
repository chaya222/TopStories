package com.example.topstories.feature.mapper

import com.example.topstories.feature.data.ArticleItem
import com.example.topstories.feature.network.TopStoriesResult

fun getTopStoriesItemFromResponse(item: TopStoriesResult) : ArticleItem{
    return ArticleItem().apply {
        title=item.title
        section=item.section
        subsection=item.subsection
    }
}