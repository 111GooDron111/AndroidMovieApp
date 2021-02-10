package com.dmabram15.androidmovieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dmabram15.androidmovieapp.view.MainMoviesFragment
import com.dmabram15.androidmoviesapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainMoviesFragment.newInstance())
                    .commitNow()
        }
    }
}