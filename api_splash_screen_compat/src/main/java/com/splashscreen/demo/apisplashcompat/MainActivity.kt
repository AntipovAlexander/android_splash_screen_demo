package com.splashscreen.demo.apisplashcompat

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.get
import com.splashscreen.demo.splashscreenempty.R

class MainActivity : AppCompatActivity() {

    private var dataIsLoaded = false
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        keepPlaceholderOnScreen()
        fakeWork()
        animateSplashScreenCompat()
    }

    private fun keepPlaceholderOnScreen() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener {
            return@addOnPreDrawListener dataIsLoaded
        }
    }

    private fun fakeWork() {
        handler.postDelayed({
            dataIsLoaded = true
        }, 2000)
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