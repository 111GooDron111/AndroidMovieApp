package com.dmabram15.androidmovieapp.view

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.dmabram15.androidmovieapp.view.bcreceivers.ConnectivityReceiver
import com.dmabram15.androidmoviesapp.R
import com.dmabram15.androidmovieapp.viewmodel.MainViewModel
import com.dmabram15.androidmoviesapp.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val receiver : ConnectivityReceiver by lazy {
        ConnectivityReceiver()
    }

    private val binding : MainActivityBinding by lazy {
        MainActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainMoviesFragment.newInstance())
            .commitAllowingStateLoss()

        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}