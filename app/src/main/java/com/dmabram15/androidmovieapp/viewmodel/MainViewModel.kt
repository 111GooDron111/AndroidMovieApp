package com.dmabram15.androidmovieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmabram15.androidmovieapp.model.DataMoviesRepositoryImp
import com.dmabram15.androidmovieapp.model.Movie
import com.dmabram15.androidmovieapp.model.MoviesRepository

class MainViewModel() : ViewModel() {

    private val liveMovieToUpdate = MutableLiveData<ArrayList<Movie>>()
    private val liveMovieShow = MutableLiveData<Movie>()
    private val liveCurrentFragment = MutableLiveData<FragmentEnum>()
    private val moviesRepository: MoviesRepository = DataMoviesRepositoryImp()
    var currentFragmentEnum : FragmentEnum = FragmentEnum.MAIN

    fun getMovies(): LiveData<ArrayList<Movie>> {
        getMovieFromData()
        return liveMovieToUpdate
    }

    private fun getMovieFromData() {
        Thread{
            liveMovieToUpdate.postValue(moviesRepository.getMovies())
        }.start()
    }

    fun clickOnMovie(movie : Movie){
        Thread{
            println("thread Start")
            liveMovieShow.postValue(movie)
            liveCurrentFragment.postValue(FragmentEnum.INFO)
            println("thread Stop")
        }.start()
    }

    fun getMovie() : LiveData<Movie> {
        return liveMovieShow
    }

    fun getCurrentFragment(currentFragmentEnum: FragmentEnum) : LiveData<FragmentEnum> {
        liveCurrentFragment.value = currentFragmentEnum
        return liveCurrentFragment
    }
}