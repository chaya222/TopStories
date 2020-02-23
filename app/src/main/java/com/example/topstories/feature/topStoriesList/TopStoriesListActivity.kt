package com.example.topstories.feature.topStoriesList

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topstories.R
import com.example.topstories.base.BaseActivity
import com.example.topstories.feature.adapter.TopStoriesAdapter
import com.example.topstories.utils.makeInVisible
import com.example.topstories.utils.makeVisible
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject.create
import kotlinx.android.synthetic.main.activity_top_stories_list.*

class TopStoriesListActivity : BaseActivity<TopStoriesListViewModel,TopStoriesListIntent,TopStoriesListViewStates>() {


    private var storiesAdapter : TopStoriesAdapter = TopStoriesAdapter()
    private val initialIntentPublisher = create<TopStoriesListIntent.InitialIntent>()
    private val swipeToRefreshIntent = create<TopStoriesListIntent.SwipeToRefresh>()

        @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            getViewModel().statesLiveData.observe(this, Observer { state ->
                render(state!!)
            })



    }

    override fun provideLayout(): Int = R.layout.activity_top_stories_list

    override fun provideViewModelClass(): Class<TopStoriesListViewModel> = TopStoriesListViewModel::class.java

    override fun startStream() {
        getViewModel().processIntents(intents())
    }

    override fun initViews() {
        setRecyclerView()
    }

    override fun render(state: TopStoriesListViewStates) {
        showProperState(state)
        if(state.initial){
            initialIntentPublisher.onNext(TopStoriesListIntent.InitialIntent)
        }
    }

    fun showProperState(state: TopStoriesListViewStates){
       if(state.isLoading){
           progressBar.makeVisible()
       }else{
           progressBar.makeInVisible()
       }

        if(state.articles.isNotEmpty()){
            storiesAdapter.updateList(state.articles)
            rvStories.makeVisible()
        }else{
            rvStories.makeInVisible()
        }

        swipeRefresh.isRefreshing = state.isRefreshing
    }

    override fun intents() = Observable.merge(
        initialIntentPublisher,
        swipeToRefreshIntent)



    private fun setRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        rvStories.layoutManager = linearLayoutManager
        rvStories.adapter = storiesAdapter
    }
}
