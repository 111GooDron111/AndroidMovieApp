package com.dmabram15.androidmovieapp.model.repository

import com.dmabram15.androidmovieapp.model.Movie

interface MoviesRepository {
    fun getMovies() : ArrayList<Movie>
}