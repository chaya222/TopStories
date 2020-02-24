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
import java.lang.Exception
import javax.inject.Inject

class TopStoriesListViewModel @Inject constructor(val topStoriesInteractor: TopStoriesInteractor) :
    BaseViewModel<TopStoriesListIntent, TopStoriesListAction, TopStoriesListResult, TopStoriesListViewStates>() {

    override val reducer: BiFunction<TopStoriesListViewStates, TopStoriesListResult, TopStoriesListViewStates> =
        BiFunction { previosState: TopStoriesListViewStates, result: TopStoriesListResult ->
          //  Log.d("stattteR", result.toString())
            when (result) {
                is TopStoriesListResult.LoadTopStoriesResult ->
                    when (result) {
                        is TopStoriesListResult.LoadTopStoriesResult.Success -> {
                            Log.d("stattteR", "1")
                            previosState.copy(
                                isLoading = false,
                                isRefreshing = false,
                                filterType = result.filterType,
                                error = null,
                                initial = false
                            )
                        }

                        is TopStoriesListResult.LoadTopStoriesResult.Failure -> {
                            Log.d("stattteR", "2")
                            previosState.copy(
                                isLoading = false,
                                error = result.error,
                                initial = false
                            )
                        }
                        is TopStoriesListResult.LoadTopStoriesResult.InProgress -> {
                            Log.d("stattteR", "3")
                            if (result.isRefreshing) {
                                previosState.copy(isLoading = false, isRefreshing = true)
                            } else previosState.copy(
                                isLoading = true,
                                isRefreshing = false,
                                initial = false,
                                articles = emptyList()
                            )
                        }
                        
                        is TopStoriesListResult.LoadTopStoriesResult.Offline -> {
                            previosState.copy(
                                initial = false,
                                articles = applyFilters(result.articles, result.filterType),
                                isLoading = false,
                                filterType = result.filterType,
                                error = Throwable("No internet Connection"),
                                isOffline = true

                            )
                        }
                    }

                is TopStoriesListResult.UpdateTopStoriesListResult ->
                    when (result) {
                        is TopStoriesListResult.UpdateTopStoriesListResult.Success -> {
                            Log.d("stattteR", "4")
                            val articles = applyFilters(result.articles, result.filterType)
                            previosState.copy(
                                initial = false,
                                articles = articles,
                                isLoading = articles.isEmpty()
                            )
                        }
                        is TopStoriesListResult.UpdateTopStoriesListResult.Failure -> {
                            Log.d("stattteR", "5")
                            previosState.copy(
                                initial = false,
                                error = result.error
                            )
                        }
                    }

            }
        }

    override fun actionFromIntent(intent: TopStoriesListIntent): TopStoriesListAction {
        Log.d("intentAction", intent.toString())
        return when (intent) {
            is TopStoriesListIntent.InitialIntent -> TopStoriesListAction.LoadStoriesListAction(
                false
            )
            is TopStoriesListIntent.SwipeToRefresh -> TopStoriesListAction.LoadStoriesListAction(
                true,filterType = intent.filterType
            )
            is TopStoriesListIntent.LoadFilteredStories -> TopStoriesListAction.LoadStoriesListAction(
                filterType = intent.filterType,
                offline = intent.offline
            )
            is TopStoriesListIntent.UpdateFilteredStories -> {
                try{
                    TopStoriesListAction.UpdateStoriesListAction(
                        filterType = intent.filterType
                    )
                }catch(ex : Exception){
                    Log.d("exceptionnnn",ex.toString())
                    TopStoriesListAction.UpdateStoriesListAction(
                        filterType = intent.filterType
                    )
                }

            }
        }
    }

    override val statesLiveData: LiveData<TopStoriesListViewStates> =
        LiveDataReactiveStreams.fromPublisher(
            intentsSubject
                .map(this::actionFromIntent)
                .doOnNext { action ->
                    Log.d("action::-", "${action}")
                }
                .compose(topStoriesInteractor.actionProcessor)
                .doOnNext { action ->
                    Log.d("action:tt:-", "${action}")
                }
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


    private fun applyFilters(
        articles: List<ArticleEntity>,
        filterType: FilterType
    ): List<ArticleEntity> {
        return when (filterType) {
            FilterType.Science -> {
                articles.filter { it.section == "science" }
            }
            FilterType.Business -> {
                articles.filter { it.section == "business" }
            }
            FilterType.World -> {
                articles.filter { it.section == "world" }
            }
            FilterType.Movies -> {
                articles.filter { it.section == "movies" }
            }
        }
    }


}