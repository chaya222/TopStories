package com.example.topstories.di.module

import com.example.topstories.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributMainActivity() : MainActivity

}