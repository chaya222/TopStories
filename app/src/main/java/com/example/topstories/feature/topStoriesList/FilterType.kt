package com.example.topstories.feature.topStoriesList

sealed class FilterType {
    object Science : FilterType()
    object Business : FilterType()
    object Movies : FilterType()
    object World : FilterType()
}