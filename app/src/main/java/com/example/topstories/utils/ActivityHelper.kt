package com.example.topstories.utils

import android.app.Activity
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.os.Build


fun Activity.isNetworkConnected(): Boolean {
    val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager?

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        cm!!.activeNetwork != null && cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
    } else {
        cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
}