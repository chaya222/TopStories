package com.example.topstories.feature.topStoriesList

import com.example.topstories.mvibase.MviIntent

sealed class TopStoriesListIntent : MviIntent {
    object InitialIntent : TopStoriesListIntent()
    object SwipeToRefresh : TopStoriesListIntent()
    data class LoadFilteredStories(val filterType: FilterType) : TopStoriesListIntent()
    data class OfflineLoadFilteredStories(val filterType: FilterType) : TopStoriesListIntent()
}