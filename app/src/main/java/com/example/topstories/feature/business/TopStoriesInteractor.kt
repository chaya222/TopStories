package com.example.topstories.feature.business


import android.util.Log
import com.example.topstories.feature.repo.TopStoriesRepo
import com.example.topstories.feature.topStoriesList.FilterType
import com.example.topstories.feature.topStoriesList.TopStoriesListAction
import com.example.topstories.feature.topStoriesList.TopStoriesListResult
import com.example.topstories.feature.topStoriesList.TopStoriesListResult.LoadTopStoriesResult
import com.example.topstories.mvibase.MviInteractor
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TopStoriesInteractor @Inject constructor(val repo : TopStoriesRepo)  :
    MviInteractor<TopStoriesListAction, TopStoriesListResult> {

    override val actionProcessor =
        ObservableTransformer<TopStoriesListAction, TopStoriesListResult> { actions ->
            actions.publish { selector ->
                Observable.merge(
                    selector.ofType(TopStoriesListAction.LoadStoriesListAction::class.java).compose(loadTopStories)
                        .doOnNext { result ->
                            Log.d("result:","$result")
                        },
                    selector.ofType(TopStoriesListAction.UpdateStoriesListAction::class.java).compose(updateTopStories)
                        .doOnNext { result ->
                            Log.d("result:","$result")
                        }
                )
            }
        }


    private val loadTopStories =
        ObservableTransformer<TopStoriesListAction.LoadStoriesListAction, TopStoriesListResult> { actions ->
            actions.flatMap { action ->
                repo.loadTopStoriesNY(getTypeFrmFilterType(action.filterType))
                    .andThen(
                        Observable.just(LoadTopStoriesResult.Success(action.filterType))
                    )
                    .cast(LoadTopStoriesResult::class.java)
                    .onErrorReturn { LoadTopStoriesResult.Failure(it) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(LoadTopStoriesResult.InProgress(action.isRefreshing,action.filterType))
            }
        }

    private fun getTypeFrmFilterType(filterType: FilterType) : String{
        return when(filterType){
            FilterType.Business->"business"
            FilterType.Movies->"movies"
            FilterType.World->"world"
            FilterType.Science->"science"
        }
    }

    private val updateTopStories =
        ObservableTransformer<TopStoriesListAction.UpdateStoriesListAction, TopStoriesListResult> { actions ->
            actions.flatMap { action ->
                var updateTopStoriesListResult : TopStoriesListResult.UpdateTopStoriesListResult? = when{
                    action.articles.isNullOrEmpty()->{
                        TopStoriesListResult.UpdateTopStoriesListResult.Failure(
                            Throwable()
                        )

                    }
                    else-> TopStoriesListResult.UpdateTopStoriesListResult.Success(action.articles)
                }
                Observable.just(updateTopStoriesListResult)
                    .cast(TopStoriesListResult.UpdateTopStoriesListResult::class.java)
                    .onErrorReturn { TopStoriesListResult.UpdateTopStoriesListResult.Failure(it) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }


}