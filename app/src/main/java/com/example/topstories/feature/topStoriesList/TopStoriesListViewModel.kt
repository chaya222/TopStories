package com.example.topstories.feature.topStoriesList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.topstories.base.BaseViewModel
import com.example.topstories.db.entity.ArticleEntity
import com.example.topstories.feature.business.TopStoriesInteractor
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class TopStoriesListViewModel @Inject constructor(val topStoriesInteractor: TopStoriesInteractor) :
    BaseViewModel<TopStoriesListIntent, TopStoriesListAction, TopStoriesListResult, TopStoriesListViewStates>() {
    override val reducer: BiFunction<TopStoriesListViewStates, TopStoriesListResult, TopStoriesListViewStates> =
        BiFunction { previosState: TopStoriesListViewStates, result: TopStoriesListResult ->
            Log.d("stattteR","555555")
            when (result) {
                is TopStoriesListResult.LoadTopStoriesResult ->
                    when (result) {
                        is TopStoriesListResult.LoadTopStoriesResult.Success ->{
                            Log.d("stattteR","1")
                            previosState.copy(
                                isLoading = false,
                                isRefreshing = false,
                                filterType = result.filterType,
                                error = null,
                                initial = false
                            )
                        }

                        is TopStoriesListResult.LoadTopStoriesResult.Failure -> {
                            Log.d("stattteR","2")
                            previosState.copy(
                                isLoading = false,
                                error = result.error,
                                initial = false
                            )
                        }
                        is TopStoriesListResult.LoadTopStoriesResult.InProgress -> {
                            Log.d("stattteR","3")
                            if (result.isRefreshing) {
                                previosState.copy(isLoading = false, isRefreshing = true)
                            } else previosState.copy(
                                isLoading = true,
                                isRefreshing = false,
                                initial = false
                            )
                        }
                    }

                is TopStoriesListResult.UpdateTopStoriesListResult ->
                    when (result) {
                        is TopStoriesListResult.UpdateTopStoriesListResult.Success -> {
                            Log.d("stattteR","4")
                            previosState.copy(
                                initial = false,
                                articles = applyFilters(result.articles, previosState.filterType),
                                isLoading = false
                            )
                        }
                        is TopStoriesListResult.UpdateTopStoriesListResult.Failure -> {
                            Log.d("stattteR","5")
                            previosState.copy(
                                initial = false,
                                error = result.error)
                        }
                    }
//
            }
        }

    override fun actionFromIntent(intent: TopStoriesListIntent): TopStoriesListAction
        {
            Log.d("intentAction",intent.toString())
           return when (intent) {
                is TopStoriesListIntent.InitialIntent -> TopStoriesListAction.LoadStoriesListAction(
                    false
                )
                is TopStoriesListIntent.SwipeToRefresh -> TopStoriesListAction.LoadStoriesListAction(
                    true
                )
            }
        }

    override val statesLiveData: LiveData<TopStoriesListViewStates> =
        LiveDataReactiveStreams.fromPublisher(
            intentsSubject
                .map(this::actionFromIntent)
                .mergeWith(topStoriesInteractor.repo.getArticlesFrmDB().map { TopStoriesListAction.UpdateStoriesListAction(it) })
                .doOnNext { action ->
                    Log.d("action::-", "${action}")
                }
                .compose(topStoriesInteractor.actionProcessor)
                // Cache each state and pass it to the reducer to create a new state from
                // the previous cached one and the latest Result emitted from the action processor.
                // The Scan operator is used here for the caching.
                .scan(TopStoriesListViewStates.idle(), reducer)
                // When a reducer just emits previousState, there's no reason to call render. In fact,
                // redrawing the UI in cases like this can cause jank (e.g. messing up snackbar animations
                // by showing the same snackbar twice in rapid succession).
                .distinctUntilChanged()
                // Emit the last one event of the stream on subscription
                // Useful when a View rebinds to the ViewModel after rotation.
                .replay(1)
                // Create the stream on creation without waiting for anyone to subscribe
                // This allows the stream to stay alive even when the UI disconnects and
                // match the stream's lifecycle to the ViewModel's one.
                .autoConnect(0)
                .toFlowable(BackpressureStrategy.BUFFER)
        )

    override fun processIntents(intents: Observable<TopStoriesListIntent>) {
        intents
            .doOnNext { intent ->
                Log.d("intent::-", "$intent")
            }
            .subscribe(intentsSubject)
    }

//    fun getTopStoriesNY() = repo.getTopStoriesNY()
//
//    fun getAllArticles() = repo.getArticlesFrmDB()


    private fun applyFilters(countries: List<ArticleEntity>, filterType: FilterType): List<ArticleEntity> =
         countries

}