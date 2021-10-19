package com.splashscreen.demo.apisplashcompat

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.get
import com.splashscreen.demo.splashscreenempty.R

class MainActivity : AppCompatActivity() {

    private var keepOnScreen = true
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splash = installSplashScreen()
        splash.setKeepVisibleCondition { keepOnScreen }
        setContentView(R.layout.activity_main)
        fakeWork()
        animateSplashScreenCompat(splash)
    }

    private fun fakeWork() {
        handler.postDelayed({ keepOnScreen = false }, 2000)
    }

    private fun animateSplashScreenCompat(splash: SplashScreen) {
        val animationDuration = 1000L
        splash.setOnExitAnimationListener { splashScreenView ->
            val animatorSet = AnimatorSet()
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenView.iconView,
                View.Y,
                0f
            )
            animatorSet.play(slideUp)
            val alpha = ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.ALPHA,
                1f,
                0f
            )
            animatorSet.play(alpha)
            (splashScreenView.view as ViewGroup).getChildAt(1)?.let {
                val slideDown = ObjectAnimator.ofFloat(
                    (splashScreenView.view as ViewGroup)[1],
                    View.Y,
                    splashScreenView.view.measuredHeight.toFloat()
                )
                animatorSet.play(slideDown)
            }
            animatorSet.apply {
                interpolator = LinearInterpolator()
                duration = animationDuration
                doOnEnd { splashScreenView.remove() }
                start()
            }
        }
    }
}