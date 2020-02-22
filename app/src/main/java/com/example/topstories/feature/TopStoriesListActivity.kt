package com.example.topstories.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topstories.R
import com.example.topstories.feature.adapter.TopStoriesAdapter
import com.example.topstories.feature.data.ArticleItem
import com.example.topstories.utils.slideUp
import kotlinx.android.synthetic.main.activity_top_stories_list.*

class TopStoriesListActivity : AppCompatActivity() {

    private var storiesAdapter : TopStoriesAdapter = TopStoriesAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_stories_list)

    }




    private fun setRecyclerView(it: List<ArticleItem>) {
        val linearLayoutManager = LinearLayoutManager(this)
        rvStories.layoutManager = linearLayoutManager
        rvStories.adapter = storiesAdapter
        storiesAdapter.updateList(it)
    }
}
