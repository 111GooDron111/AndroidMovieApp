package com.dmabram15.androidmovieapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.dmabram15.androidmoviesapp.R
import com.dmabram15.androidmovieapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
       ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //Подключение модели представления


        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainMoviesFragment.newInstance())
            .commitAllowingStateLoss()
    }
}