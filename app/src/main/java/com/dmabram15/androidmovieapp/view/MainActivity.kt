package com.dmabram15.androidmovieapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dmabram15.androidmoviesapp.R
import com.dmabram15.androidmovieapp.viewmodel.FragmentEnum
import com.dmabram15.androidmovieapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var currentFragment: FragmentEnum
    private lateinit var viewModel : MainViewModel
    private lateinit var observer: Observer<FragmentEnum>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        currentFragment = viewModel.currentFragmentEnum
        observer = Observer{
            changeFragment(it)
        }
        viewModel.getCurrentFragment(currentFragment).observe(this, observer)
    }

    private fun changeFragment(it: FragmentEnum) {
        currentFragment = it
        var fragmentManager = supportFragmentManager.beginTransaction()
        when (currentFragment) {
            FragmentEnum.MAIN -> fragmentManager.replace(
                R.id.container,
                MainMoviesFragment.newInstance()
            )
            FragmentEnum.INFO -> fragmentManager.replace(
                R.id.container,
                MovieInfoFragment.newInstance()
            )
        }
        fragmentManager.commitNow()
    }

}