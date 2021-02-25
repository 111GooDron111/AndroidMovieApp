package com.dmabram15.androidmovieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmabram15.androidmovieapp.api.MoviesAPI
import com.dmabram15.androidmovieapp.model.Movie
import com.dmabram15.androidmovieapp.model.repository.InetMoviesRepo
import com.dmabram15.androidmovieapp.model.repository.MoviesRepository

class MainMoviesFragmentViewModel() : ViewModel() {

    private val liveMovieToUpdate = MutableLiveData<ArrayList<Movie>>()
    private val moviesRepository: MoviesRepository = InetMoviesRepo()

    fun getMovies(): LiveData<ArrayList<Movie>> {
        return getMovieFromData(MoviesAPI.DAY)
    }

    fun getMovieFromData(period: String): LiveData<ArrayList<Movie>> {
        Thread {
            val movies = moviesRepository.getMoviesForPeriod(period)
            movies.let { liveMovieToUpdate.postValue(it) }
        }.start()
        return liveMovieToUpdate
    }
}