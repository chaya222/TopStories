package com.example.topstories.feature.adapter

import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.topstories.R
import com.example.topstories.db.entity.ArticleEntity
import com.example.topstories.feature.data.ArticleItem
import com.example.topstories.feature.listener.TopStoryListener
import com.example.topstories.utils.makeInVisible
import kotlinx.android.synthetic.main.activity_top_stories_details.*
import kotlin.collections.ArrayList

class TopStoriesAdapter : RecyclerView.Adapter<TopStoriesAdapter.TopStoriesViewHolder>() {

    private var topStoriesList = ArrayList<ArticleItem>()
    private var topStoryListener : TopStoryListener? = null

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
        holder.tvSection.text =  if(itemArticle.byline.isNotEmpty()) itemArticle.byline else "Unknown"

        Glide.with(holder.itemView.context)
            .load(itemArticle.multimediaUrl)
            .placeholder(R.drawable.place_holder) //placeholder
            .error(R.drawable.place_holder) //error
            .into(holder.imvThumb)

    }

    inner class TopStoriesViewHolder(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            topStoryListener?.onTopStoryClicked(topStoriesList[adapterPosition].title)
        }

        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvSection = view.findViewById<TextView>(R.id.tvSection)
        val imvThumb = view.findViewById<ImageView>(R.id.imvThumb)

    }

    fun updateList(list: List<ArticleEntity>) {
        topStoriesList.clear()
        for(i in list){
            topStoriesList.add(ArticleItem().apply {
                title=i.title
                section=i.section
                subsection=i.subsection
                multimediaUrl=i.multimediaUrl
                articleUrl=i.articleUrl
                published_date=i.published_date
                byline=i.byline
                abstract=i.abstract
            })
        }
        notifyDataSetChanged()
    }

    fun setTopStoryClickListener(topStoryClickListener: TopStoryListener){
        topStoryListener=topStoryClickListener
    }

}