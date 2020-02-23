package com.example.topstories.feature.topStoriesList

import com.example.topstories.mvibase.MviIntent

sealed class TopStoriesListIntent : MviIntent {
    object InitialIntent : TopStoriesListIntent()
    object SwipeToRefresh : TopStoriesListIntent()

}