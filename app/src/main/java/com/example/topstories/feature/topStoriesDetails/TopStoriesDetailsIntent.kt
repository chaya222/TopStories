package com.example.topstories.feature.topStoriesDetails

import com.example.topstories.mvibase.MviIntent


sealed class TopStoriesDetailsIntent : MviIntent {
    data class LoadStoryDetailsIntent(val name: String?) : TopStoriesDetailsIntent()

}