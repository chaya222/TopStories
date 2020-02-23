package com.example.topstories.feature.topStoriesList

import com.example.topstories.db.entity.ArticleEntity
import com.example.topstories.mvibase.MviViewState


data class TopStoriesListViewStates(
    val isLoading: Boolean,
    val isRefreshing: Boolean,
    val articles: List<ArticleEntity>,
    val filterType: FilterType,
    val error: Throwable?,
    val initial: Boolean
) : MviViewState {
    companion object {
        fun idle(): TopStoriesListViewStates {
            return TopStoriesListViewStates(
                isLoading = false,
                isRefreshing = false,
                articles = emptyList(),
                filterType = FilterType.All,
                error = null,
                initial = true
            )
        }
    }
}