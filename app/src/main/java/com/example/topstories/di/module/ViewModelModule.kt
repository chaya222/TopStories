package com.example.topstories.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.topstories.di.factory.ViewModelFactory
import com.example.topstories.di.scope.ViewModelKey
import com.example.topstories.feature.topStoriesDetails.TopStoriesDetailsViewModel
import com.example.topstories.feature.topStoriesList.TopStoriesListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule{

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TopStoriesListViewModel::class)
    internal abstract fun bindTopStoriesViewModel(topStoriesListViewModel: TopStoriesListViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopStoriesDetailsViewModel::class)
    internal abstract fun bindTopStoriesDetailsViewModel(topStoriesDetailsViewModel: TopStoriesDetailsViewModel) : ViewModel
}