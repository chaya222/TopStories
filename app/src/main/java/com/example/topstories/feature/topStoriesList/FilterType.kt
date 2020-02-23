package com.example.topstories.feature.topStoriesList

sealed class FilterType {
    object All : FilterType()
    object Favorite : FilterType()
}