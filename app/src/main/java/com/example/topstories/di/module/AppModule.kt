package com.example.topstories.di.module

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.topstories.TopStoriesApp
import com.example.topstories.api.ApiInterface
import com.example.topstories.db.TopStoriesDatabase
import com.example.topstories.db.TopStoriesDatabase.Companion.databaseName
import com.example.topstories.db.dao.TopStoriesDao
import com.example.topstories.feature.business.TopStoriesInteractor
import com.example.topstories.feature.repo.TopStoriesRepo
import com.example.topstories.utils.AppRxSchedulers
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule{

    @Singleton
    @Provides
    fun provideRepo(api : ApiInterface, rxSchedulers: AppRxSchedulers,topStoriesDao: TopStoriesDao) : TopStoriesRepo = TopStoriesRepo(api = api,rxSchedulers = rxSchedulers,topStoriesDao = topStoriesDao)

    @Singleton
    @Provides
    fun getDB(context : TopStoriesApp) : TopStoriesDatabase{
        return  Room.databaseBuilder(context, TopStoriesDatabase::class.java, databaseName).build()
    }

    @Singleton
    @Provides
    fun getTopStoriesDao(db : TopStoriesDatabase) : TopStoriesDao{
        return db.topStoriesDao()
    }

    @Singleton
    @Provides
    fun provideTopStoriesListInstructor(repo : TopStoriesRepo) = TopStoriesInteractor(repo)
}