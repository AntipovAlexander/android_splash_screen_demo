package com.splashscreen.demo.themesplash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.splashscreen.demo.splashscreenempty.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_App)
        setContentView(R.layout.activity_main)
    }
}