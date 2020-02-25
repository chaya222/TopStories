package com.example.topstories.feature.ui.topStoriesDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.topstories.base.BaseViewModel
import com.example.topstories.feature.business.TopStoryDetailsInteractor
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject


class TopStoriesDetailsViewModel @Inject constructor(topStoriesInteractor: TopStoryDetailsInteractor) :
    BaseViewModel<TopStoriesDetailsIntent, TopStoriesDetailsAction, TopStoriesDetailsResult, TopStoriesDetailsViewStates>() {

    override val reducer =
        BiFunction { previousState: TopStoriesDetailsViewStates, result: TopStoriesDetailsResult ->
            when (result) {

                is TopStoriesDetailsResult.LoadTopStoryDetailsResult.Success -> previousState.copy(
                    isLoading = false,
                    article = result.article,
                    initial = false
                )
                is TopStoriesDetailsResult.LoadTopStoryDetailsResult.Failure -> previousState.copy(
                    isLoading = false,
                    error = result.error,
                    initial = false
                )
                is TopStoriesDetailsResult.LoadTopStoryDetailsResult.InProgress -> previousState.copy(
                    isLoading = true,
                    initial = false
                )

            }
        }

    override val statesLiveData: LiveData<TopStoriesDetailsViewStates> =
        LiveDataReactiveStreams.fromPublisher(
            intentsSubject
                .map(this::actionFromIntent)
                .compose(topStoriesInteractor.actionProcessor)
                // Cache each state and pass it to the reducer to create a new state from
                // the previous cached one and the latest Result emitted from the action processor.
                // The Scan operator is used here for the caching.
                .scan(TopStoriesDetailsViewStates.idle(), reducer)
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

    override fun processIntents(intents: Observable<TopStoriesDetailsIntent>) =
        intents
            .subscribe(intentsSubject)

    override fun actionFromIntent(intent: TopStoriesDetailsIntent): TopStoriesDetailsAction =
        when (intent) {
            is TopStoriesDetailsIntent.IntialIntent -> TopStoriesDetailsAction.LoadStoryDetailAction(
                intent.name
            )
        }
}