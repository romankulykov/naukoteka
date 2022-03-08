package uddug.com.naukoteka.utils.ui

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator

fun View.fadeInOut() {
    val fadeIn = AlphaAnimation(0f, 1f)
    fadeIn.interpolator = DecelerateInterpolator()
    fadeIn.duration = 1000


    val animation = AnimationSet(false) //change to false
    animation.addAnimation(fadeIn)
    this.animation = animation
}