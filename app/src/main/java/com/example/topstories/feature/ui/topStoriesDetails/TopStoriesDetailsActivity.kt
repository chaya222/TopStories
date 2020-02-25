package com.example.topstories.feature.ui.topStoriesDetails

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.topstories.R
import com.example.topstories.base.BaseActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject.create
import kotlinx.android.synthetic.main.activity_top_stories_details.*
import java.text.SimpleDateFormat

class TopStoriesDetailsActivity :
    BaseActivity<TopStoriesDetailsViewModel, TopStoriesDetailsIntent, TopStoriesDetailsViewStates>() {


    companion object {
        fun newInstance(context: Context, name: String): Intent {
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
        imvBack.setOnClickListener {
            finish()
        }
    }

    override fun render(state: TopStoriesDetailsViewStates) {
        showProperState(state)
    }


    private fun showProperState(state: TopStoriesDetailsViewStates) {
        state.article?.let {
            Glide.with(this)
                .load(it.multimediaUrl)
                .placeholder(R.drawable.place_holder) //placeholder
                .error(R.drawable.place_holder) //error
                .into(imvBackCover)
            tvAuthor.text=if(it.byline.isNotEmpty()) it.byline else "Unknown"
            val pubDate = SimpleDateFormat("dd MMM yyyy").format(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-05:00").parse(it.published_date))
            tvPublish.text="Published on: $pubDate "
            val txt ="${it.articleUrl}"
            tvLink.text = txt
            tvTitle.text = it.title
            tvAbstract.text=it.abstract

        }

    }

    override fun intents(): Observable<TopStoriesDetailsIntent> {
        return initialIntentPublisher.cast(TopStoriesDetailsIntent::class.java)
    }
}






