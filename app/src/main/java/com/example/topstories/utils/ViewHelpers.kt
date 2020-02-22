package com.example.topstories.utils

import android.view.View
import android.view.animation.TranslateAnimation


fun View.slideUp() {
    this.visibility = View.VISIBLE
    val animate = TranslateAnimation(
        0f,
        0f,
        this.height.toFloat(),
        0f
    )
    animate.duration = 500
    animate.fillAfter = true
    this.startAnimation(animate)
}


fun View.makeVisible(){
      visibility=View.VISIBLE
}

fun View.makeInVisible(){
    visibility=View.INVISIBLE
}

fun View.makeGone(){
    visibility=View.GONE
}