package com.dmabram15.androidmovieapp.viewmodel

import com.dmabram15.androidmovieapp.model.Movie

interface OnMovieCardClickListener {
    fun onMovieCardClick(movie: Movie)
}