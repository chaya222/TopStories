package com.example.topstories

import com.example.topstories.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class TopStoriesApp : DaggerApplication(){


    init {
        instance=this
    }

    companion object{
        private var instance : TopStoriesApp?=null
    }


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .builder()
            .application(this)
            .build()


    }

}