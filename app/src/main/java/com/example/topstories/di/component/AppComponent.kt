package com.example.topstories.di.component


import com.example.topstories.TopStoriesApp
import com.example.topstories.di.module.ApiModule
import com.example.topstories.di.module.ContextModule
import com.example.topstories.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
    AndroidSupportInjectionModule::class,
    ApiModule::class,
    ContextModule::class,
    ViewModelModule::class
    ]
)
interface AppComponent  : AndroidInjector<TopStoriesApp>{

    @Component.Builder
    interface  Builder{
        @BindsInstance
        fun application(app: TopStoriesApp): Builder

        fun build():AppComponent
    }


}