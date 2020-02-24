package com.example.topstories.feature.topStoriesDetails

import com.example.topstories.mvibase.MviAction

sealed class TopStoriesDetailsAction : MviAction {
    data class LoadStoryDetailAction(val name: String?) : TopStoriesDetailsAction()

}