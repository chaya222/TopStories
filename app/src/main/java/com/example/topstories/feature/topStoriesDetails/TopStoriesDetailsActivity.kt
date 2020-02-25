package com.example.topstories.feature.topStoriesDetails

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.topstories.R
import com.example.topstories.base.BaseActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject.create

class TopStoriesDetailsActivity :
    BaseActivity<TopStoriesDetailsViewModel, TopStoriesDetailsIntent, TopStoriesDetailsViewStates>()
    {
    companion object {
        fun newInstance(context: Context,  name: String): Intent {
            return Intent(context, TopStoriesDetailsActivity::class.java).apply {
                putExtra("name", name)
            }
        }
    }

    private val initialIntentPublisher = create<TopStoriesDetailsIntent.IntialIntent>()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel().statesLiveData.observe(this, Observer { state ->
            render(state!!)
        })

        intent?.let {
            it.getStringExtra("name")?.let {
                initialIntentPublisher.onNext(TopStoriesDetailsIntent.IntialIntent(it))
            }
        }


    }

    override fun provideLayout(): Int = R.layout.activity_top_stories_details

    override fun provideViewModelClass(): Class<TopStoriesDetailsViewModel> =
        TopStoriesDetailsViewModel::class.java

    override fun startStream() {
        getViewModel().processIntents(intents())
    }

    override fun initViews() {
    }

    override fun render(state: TopStoriesDetailsViewStates) {
        showProperState(state)
    }


    private fun showProperState(state: TopStoriesDetailsViewStates) {

//        if (state.isLoading) {
//            progressBar.makeVisible()
//        } else {
//            progressBar.makeInVisible()
//        }
//
//        if (state.articles.isNotEmpty()) {
//            storiesAdapter.updateList(state.articles)
//            rvStories.makeVisible()
//        } else {
//
//            rvStories.makeInVisible()
//        }
//
//        if (state.isOffline) {
//            if (state.articles.isEmpty()) {
//                state.error?.let {
//                    Snackbar.make(clParent, "No internet connection", Snackbar.LENGTH_LONG)
//                        .setAction("CLOSE") { }
//                        .setActionTextColor(resources.getColor(android.R.color.holo_red_light))
//                        .show()
//                }
//            }
//
//        }
//
//        swipeRefresh.isRefreshing = state.isRefreshing
    }

    override fun intents() : Observable<TopStoriesDetailsIntent>{
        return initialIntentPublisher.cast(TopStoriesDetailsIntent::class.java)
    }


}

