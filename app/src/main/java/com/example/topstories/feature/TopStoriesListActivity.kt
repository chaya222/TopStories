package com.example.topstories.feature

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topstories.R
import com.example.topstories.base.BaseActivity
import com.example.topstories.db.entity.ArticleEntity
import com.example.topstories.feature.adapter.TopStoriesAdapter
import com.example.topstories.feature.data.ArticleItem
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_top_stories_list.*
import org.reactivestreams.Subscriber

class TopStoriesListActivity : BaseActivity<TopStoriesListViewModel>() {
    override fun provideLayout(): Int = R.layout.layout_rv_top_stories

    override fun provideViewModelClass(): Class<TopStoriesListViewModel> = TopStoriesListViewModel::class.java

    private var storiesAdapter : TopStoriesAdapter = TopStoriesAdapter()
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_stories_list)

        getViewModel().getTopStoriesNY()

        LiveDataReactiveStreams.fromPublisher(getViewModel().getAllArticles())
            .observe(this, Observer {
                it?.let {
                    Log.d("cooooo",it.size.toString())
                }

            })


//            .subscribeOn(Schedulers.io())
//            .subscribeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//            it?.let {
//                Log.d("kdfh",it.size.toString())
//            }
//        }

    }


    private fun setRecyclerView(it: List<ArticleItem>) {
        val linearLayoutManager = LinearLayoutManager(this)
        rvStories.layoutManager = linearLayoutManager
        rvStories.adapter = storiesAdapter
        storiesAdapter.updateList(it)
    }
}
