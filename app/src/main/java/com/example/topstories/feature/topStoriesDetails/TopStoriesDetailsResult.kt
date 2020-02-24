package com.example.topstories.feature.topStoriesDetails

import com.example.topstories.db.entity.ArticleEntity
import com.example.topstories.mvibase.MviResult


sealed class TopStoriesDetailsResult : MviResult {
    sealed class LoadTopStoryDetailsResult : TopStoriesDetailsResult() {
        data class Success(val article : ArticleEntity) : TopStoriesDetailsResult()
        data class Failure(val error: Throwable) : TopStoriesDetailsResult()
        object InProgress : TopStoriesDetailsResult()
    }

}