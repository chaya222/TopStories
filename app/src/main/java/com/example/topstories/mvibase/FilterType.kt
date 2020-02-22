package com.example.topstories.mvibase

sealed class FilterType {
	object All : FilterType()
	object Favorite : FilterType()
}