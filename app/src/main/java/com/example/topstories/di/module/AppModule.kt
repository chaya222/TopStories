package com.example.topstories.di.module

import com.example.topstories.api.ApiInterface
import com.example.topstories.feature.business.TopStoriesInstructor
import com.example.topstories.feature.repo.TopStoriesRepo
import com.example.topstories.utils.AppRxSchedulers
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule{

    @Singleton
    @Provides
    fun provideRepo(api : ApiInterface, rxSchedulers: AppRxSchedulers) : TopStoriesRepo = TopStoriesRepo(api = api,rxSchedulers = rxSchedulers)


//    @Singleton
//    @Provides
//    fun provideTopStoriesListInstructor(repo : TopStoriesRepo) = TopStoriesInstructor(repo)
}