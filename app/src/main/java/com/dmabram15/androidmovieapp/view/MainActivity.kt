package com.dmabram15.androidmovieapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dmabram15.androidmoviesapp.R
import com.dmabram15.androidmovieapp.viewmodel.FragmentEnum
import com.dmabram15.androidmovieapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //Подключение модели представления
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainMoviesFragment.newInstance())
            .commitAllowingStateLoss()
    }
}