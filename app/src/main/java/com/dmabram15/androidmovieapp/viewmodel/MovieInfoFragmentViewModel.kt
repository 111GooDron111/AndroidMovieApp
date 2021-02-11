package com.dmabram15.androidmovieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmabram15.androidmovieapp.model.Movie

class MovieInfoFragmentViewModel() : ViewModel() {

    private val liveMovieShow = MutableLiveData<Movie>()


    fun getMovie() : LiveData<Movie> {
        return liveMovieShow
    }

}