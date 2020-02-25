package com.example.topstories.feature.ui.topStoriesList

sealed class FilterType {
    object Science : FilterType()
    object Business : FilterType()
    object Movies : FilterType()
    object World : FilterType()
}