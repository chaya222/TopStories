package com.example.topstories.di.module

import android.content.Context
import com.example.topstories.TopStoriesApp
import dagger.Module
import dagger.Provides


@Module
class ContextModule{
    @Provides
    fun provideContext(topStoriesApp: TopStoriesApp):Context{
        return topStoriesApp.applicationContext
    }
}