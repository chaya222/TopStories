package com.example.topstories.feature.repo


import com.example.topstories.api.ApiInterface
import com.example.topstories.db.dao.TopStoriesDao
import com.example.topstories.db.entity.ArticleEntity
import com.example.topstories.feature.mapper.getTopStoriesEntityFromResponse
import com.example.topstories.utils.AppRxSchedulers
import io.reactivex.Completable
import javax.inject.Inject

class TopStoriesRepo @Inject constructor(
    val api: ApiInterface,
    val rxSchedulers: AppRxSchedulers,
    val topStoriesDao: TopStoriesDao
) {


    fun loadTopStoriesNY(type:String) : Completable =
        api.getTopStoriesNY(type)
            .doOnSuccess { articleResponse ->
                val articleList: ArrayList<ArticleEntity> = ArrayList()
                articleResponse.articleList.let {
                    for (item in it) {
                        articleList.add(getTopStoriesEntityFromResponse(item))
                    }
                    saveArticleInDB(articleList)

                }
            }.ignoreElement()


    fun getArticlesFrmDB() = topStoriesDao.getAllArticle().toObservable()

    fun saveArticleInDB(list: List<ArticleEntity>) = topStoriesDao.insertFetchedArticle(list)


}