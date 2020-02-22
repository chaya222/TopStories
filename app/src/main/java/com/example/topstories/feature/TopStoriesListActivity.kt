package com.example.topstories.feature

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topstories.R
import com.example.topstories.base.BaseActivity
import com.example.topstories.feature.adapter.TopStoriesAdapter
import com.example.topstories.feature.data.ArticleItem
import kotlinx.android.synthetic.main.activity_top_stories_list.*

class TopStoriesListActivity : BaseActivity<TopStoriesListViewModel>() {
    override fun provideLayout(): Int = R.layout.layout_rv_top_stories

    override fun provideViewModelClass(): Class<TopStoriesListViewModel> = TopStoriesListViewModel::class.java

    private var storiesAdapter : TopStoriesAdapter = TopStoriesAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_stories_list)

        getViewModel().getTopStoriesNY().observe(this, Observer {
            it?.let {
                setRecyclerView(it)
            }
        })
    }


    private fun setRecyclerView(it: List<ArticleItem>) {
        val linearLayoutManager = LinearLayoutManager(this)
        rvStories.layoutManager = linearLayoutManager
        rvStories.adapter = storiesAdapter
        storiesAdapter.updateList(it)
    }
}
