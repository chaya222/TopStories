package com.example.topstories.feature.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.topstories.R
import com.example.topstories.feature.data.ArticleItem
import com.example.topstories.utils.makeInVisible
import kotlin.collections.ArrayList

class TopStoriesAdapter : RecyclerView.Adapter<TopStoriesAdapter.TopStoriesViewHolder>() {

    private var topStoriesList = ArrayList<ArticleItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopStoriesViewHolder {
        return TopStoriesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_rv_top_stories,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return topStoriesList.size
    }

    override fun onBindViewHolder(holder: TopStoriesViewHolder, position: Int) {
        val itemArticle = topStoriesList.get(position)

        holder.tvTitle.text = itemArticle.title
        holder.tvSection.text =  itemArticle.section

        if (topStoriesList.size.minus(1) == position)
            holder.vwDivider.makeInVisible()

    }

    inner class TopStoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvSection = view.findViewById<TextView>(R.id.tvSection)
        val vwDivider = view.findViewById<View>(R.id.vwDivider)

    }

    fun updateList(list: List<ArticleItem>) {
        topStoriesList.clear()
        for(i in list){
            topStoriesList.add(ArticleItem().apply {
                title=i.title
                section=i.section
                subsection=i.subsection
            })
        }


    }

}