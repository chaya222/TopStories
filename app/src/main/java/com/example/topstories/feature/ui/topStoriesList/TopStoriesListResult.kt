package com.example.topstories.feature.ui.topStoriesList

import com.example.topstories.db.entity.ArticleEntity
import com.example.topstories.mvibase.MviResult


sealed class TopStoriesListResult : MviResult {
    sealed class LoadTopStoriesResult : TopStoriesListResult() {
        data class Success(val filterType: FilterType) : LoadTopStoriesResult()
        data class Failure(val error: Throwable) : LoadTopStoriesResult()
        data class InProgress(val isRefreshing: Boolean,val filterType: FilterType) : LoadTopStoriesResult()
        data class Offline(val articles : List<ArticleEntity>,val filterType: FilterType) : LoadTopStoriesResult()
    }

    sealed class UpdateTopStoriesListResult : TopStoriesListResult() {
        data class Success(val articles : List<ArticleEntity>,val filterType: FilterType) : UpdateTopStoriesListResult()
        data class Failure(val error: Throwable) : UpdateTopStoriesListResult()
    }

}