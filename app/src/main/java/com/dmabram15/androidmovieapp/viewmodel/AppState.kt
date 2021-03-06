package com.dmabram15.androidmovieapp.viewmodel

import com.dmabram15.androidmovieapp.model.Movie

sealed class AppState {
    data class Success(val moviesData: List<Movie>,
    val genreListName : String?) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
