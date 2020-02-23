package com.example.topstories.feature.topStoriesList

import com.example.topstories.db.entity.ArticleEntity
import com.example.topstories.mvibase.MviResult


sealed class TopStoriesListResult : MviResult {
    sealed class LoadCountriesResult : TopStoriesListResult() {
        data class Success(val filterType: FilterType) : LoadCountriesResult()
        data class Failure(val error: Throwable) : LoadCountriesResult()
        data class InProgress(val isRefreshing: Boolean) : LoadCountriesResult()
    }

    sealed class UpdateCountryListResult : TopStoriesListResult() {
        data class Success(val countries: List<ArticleEntity>) : UpdateCountryListResult()
        data class Failure(val error: Throwable) : UpdateCountryListResult()
    }

}