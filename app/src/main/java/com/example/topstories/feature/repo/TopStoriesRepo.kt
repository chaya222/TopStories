package com.example.topstories.feature.repo

import com.example.topstories.api.ApiInterface
import com.example.topstories.utils.AppRxSchedulers
import javax.inject.Inject

class TopStoriesRepo @Inject constructor(val api : ApiInterface, val rxSchedulers: AppRxSchedulers){


}