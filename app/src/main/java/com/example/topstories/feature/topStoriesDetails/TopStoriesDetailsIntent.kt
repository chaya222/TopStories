package com.example.topstories.feature.topStoriesDetails

import com.example.topstories.mvibase.MviIntent


sealed class TopStoriesDetailsIntent : MviIntent {
    data class IntialIntent(val name: String?) : TopStoriesDetailsIntent()
    data class LoadStoryDetailsIntent(val name: String?) : TopStoriesDetailsIntent()

}