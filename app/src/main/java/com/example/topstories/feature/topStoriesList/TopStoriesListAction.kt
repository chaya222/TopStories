package com.example.topstories.feature.topStoriesList

import com.example.topstories.db.entity.ArticleEntity
import com.example.topstories.mvibase.MviAction

sealed class TopStoriesListAction : MviAction {
    data class LoadStoriesListAction(val isRefreshing: Boolean = false, val filterType: FilterType = FilterType.All) : TopStoriesListAction()
    data class UpdateStoriesListAction(val countries: List<ArticleEntity>) : TopStoriesListAction()
}