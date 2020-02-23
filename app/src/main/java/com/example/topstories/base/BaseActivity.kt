package com.example.topstories.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.topstories.mvibase.MviIntent
import com.example.topstories.mvibase.MviView
import com.example.topstories.mvibase.MviViewState
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel, I : MviIntent, S : MviViewState> : DaggerAppCompatActivity(),
    MviView<I, S> {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: VM

    abstract fun startStream()

    abstract fun initViews()

    @LayoutRes
    protected abstract fun provideLayout() : Int

    protected abstract fun provideViewModelClass() : Class<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(provideLayout())
        viewModel= ViewModelProviders
            .of(this,viewModelFactory)
            .get(provideViewModelClass())
        initViews()
        startStream()
    }

    protected fun getViewModel() : VM{
        return viewModel
    }


}