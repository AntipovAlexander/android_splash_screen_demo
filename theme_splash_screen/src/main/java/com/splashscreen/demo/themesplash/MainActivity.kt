package com.splashscreen.demo.themesplash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.view.get
import com.splashscreen.demo.splashscreenempty.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_App)
        setContentView(R.layout.activity_main)
        animateSplashScreenCompat()
    }

    private fun animateSplashScreenCompat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val animationDuration = 1000L
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                val animatorSet = AnimatorSet()
                val slideUp = ObjectAnimator.ofFloat(
                    splashScreenView.iconView,
                    View.Y,
                    0f
                )
                val alpha = ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.ALPHA,
                    1f,
                    0f
                )
                val slideDown = ObjectAnimator.ofFloat(
                    splashScreenView[1],
                    View.Y,
                    splashScreenView.measuredHeight.toFloat()
                )
                animatorSet.apply {
                    playTogether(slideUp, alpha, slideDown)
                    interpolator = LinearInterpolator()
                    duration = animationDuration
                    doOnEnd { splashScreenView.remove() }
                    start()
                }
            }
        }
    }
}