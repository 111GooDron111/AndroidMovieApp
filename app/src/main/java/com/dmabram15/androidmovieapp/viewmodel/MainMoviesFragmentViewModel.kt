package com.dmabram15.androidmovieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmabram15.androidmovieapp.model.repository.DataMoviesRepositoryImp
import com.dmabram15.androidmovieapp.model.Movie
import com.dmabram15.androidmovieapp.model.repository.MoviesRepository

class MainMoviesFragmentViewModel() : ViewModel() {

    private val liveMovieToUpdate = MutableLiveData<ArrayList<Movie>>()

    private val moviesRepository: MoviesRepository = DataMoviesRepositoryImp()

    fun getMovies(): LiveData<ArrayList<Movie>> {
        return liveMovieToUpdate
    }

    fun getMovieFromData() {
        liveMovieToUpdate.value = moviesRepository.getMovies()
    }
}