package com.example.topstories.api

import com.example.topstories.feature.network.ArticleResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("{TYPE}.json?api-key=xI4AA4gcMj9JyFlyQn2dSAj689PGjKjA")
    fun getTopStoriesNY(@Path("TYPE") type : String): Single<ArticleResponse>
}