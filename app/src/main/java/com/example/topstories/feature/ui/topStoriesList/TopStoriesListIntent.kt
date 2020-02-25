package com.example.topstories.feature.ui.topStoriesList

import com.example.topstories.mvibase.MviIntent

sealed class TopStoriesListIntent : MviIntent {
    object InitialIntent : TopStoriesListIntent()
    data class SwipeToRefresh(val filterType: FilterType) : TopStoriesListIntent()
    data class LoadFilteredStories(val filterType: FilterType,val offline : Boolean = false) : TopStoriesListIntent()
    data class UpdateFilteredStories(val filterType: FilterType) : TopStoriesListIntent()
}