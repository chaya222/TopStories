package com.example.topstories.api

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("{TYPE}.json?api-key={KEY}")
    fun getTopStoriesNY(@Path("KEY") key: String,@Path("TYPE") type : String): Void
}