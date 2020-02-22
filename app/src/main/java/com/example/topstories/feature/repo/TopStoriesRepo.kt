package com.example.topstories.feature.repo

import androidx.lifecycle.MutableLiveData
import com.example.topstories.api.ApiInterface
import com.example.topstories.feature.data.ArticleItem
import com.example.topstories.feature.mapper.getTopStoriesItemFromResponse
import com.example.topstories.feature.network.ArticleResponse
import com.example.topstories.utils.AppRxSchedulers
import com.example.topstories.utils.Constants
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class TopStoriesRepo @Inject constructor(val api: ApiInterface, val rxSchedulers: AppRxSchedulers) {


        fun getTopStoriesNY(): MutableLiveData<List<ArticleItem>> {
        val mutableLiveData = MutableLiveData<List<ArticleItem>>()
        api.getTopStoriesNY( "arts")
            .subscribeOn(rxSchedulers.io())
            .observeOn(rxSchedulers.androidThread())
            .subscribe(object : SingleObserver<ArticleResponse> {
                override fun onSuccess(t: ArticleResponse) {
                    val articleList: ArrayList<ArticleItem> = ArrayList()
                    t.articleList.let {
                        for (item in it) {
                            articleList.add(getTopStoriesItemFromResponse(item))
                        }
                        mutableLiveData.value = articleList
                    }
                }

                override fun onSubscribe(d: Disposable) {}

                override fun onError(e: Throwable) {}

            })
        return mutableLiveData
    }


}