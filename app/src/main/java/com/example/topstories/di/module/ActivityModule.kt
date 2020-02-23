package com.example.topstories.di.module

import com.example.topstories.feature.topStoriesList.TopStoriesListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contribuTopStoriesActivity() : TopStoriesListActivity

}