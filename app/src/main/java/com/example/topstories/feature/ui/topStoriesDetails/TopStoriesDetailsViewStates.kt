package com.example.topstories.feature.ui.topStoriesDetails

import com.example.topstories.db.entity.ArticleEntity
import com.example.topstories.mvibase.MviViewState

data class TopStoriesDetailsViewStates(
    val isLoading: Boolean,
    val article: ArticleEntity?,
    val error: Throwable?,
    val initial: Boolean
) : MviViewState {
    companion object {
        fun idle(): TopStoriesDetailsViewStates {
            return TopStoriesDetailsViewStates(
                isLoading = false,
                article = null,
                error = null,
                initial = true
            )
        }
    }
}