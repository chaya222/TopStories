package com.example.topstories.feature.topStoriesList

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topstories.R
import com.example.topstories.base.BaseActivity
import com.example.topstories.feature.adapter.TopStoriesAdapter
import com.example.topstories.utils.isNetworkConnected
import com.example.topstories.utils.makeInVisible
import com.example.topstories.utils.makeVisible
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject.create
import kotlinx.android.synthetic.main.activity_top_stories_list.*

class TopStoriesListActivity :
    BaseActivity<TopStoriesListViewModel, TopStoriesListIntent, TopStoriesListViewStates>(),
    View.OnClickListener {

    private var storiesAdapter: TopStoriesAdapter = TopStoriesAdapter()
    private val initialIntentPublisher = create<TopStoriesListIntent.InitialIntent>()
    private val swipeToRefreshIntent = create<TopStoriesListIntent.SwipeToRefresh>()
    private val loadFilteredStories = create<TopStoriesListIntent.LoadFilteredStories>()
    private val offlineLoadFilteredStories =
        create<TopStoriesListIntent.OfflineLoadFilteredStories>()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel().statesLiveData.observe(this, Observer { state ->
            render(state!!)
        })

        swipeRefresh.setOnRefreshListener {
            swipeToRefreshIntent.onNext(TopStoriesListIntent.SwipeToRefresh)
        }
    }

    override fun provideLayout(): Int = R.layout.activity_top_stories_list

    override fun provideViewModelClass(): Class<TopStoriesListViewModel> =
        TopStoriesListViewModel::class.java

    override fun startStream() {
        getViewModel().processIntents(intents())
    }

    override fun initViews() {
        setRecyclerView()
        setClickListeners()
    }

    override fun render(state: TopStoriesListViewStates) {
        showProperState(state)
        if (state.initial) {
            initialIntentPublisher.onNext(TopStoriesListIntent.InitialIntent)
        }
    }

    private fun setClickListeners() {
        clBtmSectionBusiness.setOnClickListener(this)
        clBtmSectionMovie.setOnClickListener(this)
        clBtmSectionScience.setOnClickListener(this)
        clBtmSectionWorld.setOnClickListener(this)
    }

    private fun showProperState(state: TopStoriesListViewStates) {
        Log.d("stttte", state.toString())
        if (state.isLoading) {
            progressBar.makeVisible()
        } else {
            progressBar.makeInVisible()
        }

        if (state.articles.isNotEmpty()) {
            storiesAdapter.updateList(state.articles)
            rvStories.makeVisible()
        } else {
            rvStories.makeInVisible()
        }

        swipeRefresh.isRefreshing = state.isRefreshing
    }

    override fun intents() = Observable.merge(
        initialIntentPublisher,
        swipeToRefreshIntent,
        loadFilteredStories,
        offlineLoadFilteredStories
    )


    private fun setRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        rvStories.layoutManager = linearLayoutManager
        rvStories.adapter = storiesAdapter
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            clBtmSectionBusiness.id -> {
                if (isNetworkConnected())
                    loadFilteredStories.onNext(TopStoriesListIntent.LoadFilteredStories(FilterType.Business))
                else
                    offlineLoadFilteredStories.onNext(
                        TopStoriesListIntent.OfflineLoadFilteredStories(
                            FilterType.Business
                        )
                    )
            }
            clBtmSectionMovie.id -> {
                if (isNetworkConnected())
                    loadFilteredStories.onNext(TopStoriesListIntent.LoadFilteredStories(FilterType.Movies))
                else
                    offlineLoadFilteredStories.onNext(
                        TopStoriesListIntent.OfflineLoadFilteredStories(
                            FilterType.Movies
                        )
                    )
            }
            clBtmSectionScience.id -> {
                if (isNetworkConnected())
                    loadFilteredStories.onNext(TopStoriesListIntent.LoadFilteredStories(FilterType.Science))
                else
                    offlineLoadFilteredStories.onNext(
                        TopStoriesListIntent.OfflineLoadFilteredStories(
                            FilterType.Science
                        )
                    )
            }
            clBtmSectionWorld.id -> {
                if (isNetworkConnected())
                    loadFilteredStories.onNext(TopStoriesListIntent.LoadFilteredStories(FilterType.World))
                else
                    offlineLoadFilteredStories.onNext(
                        TopStoriesListIntent.OfflineLoadFilteredStories(
                            FilterType.World
                        )
                    )
            }
        }
    }
}
