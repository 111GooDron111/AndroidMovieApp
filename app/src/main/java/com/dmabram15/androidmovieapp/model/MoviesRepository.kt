package com.dmabram15.androidmovieapp.model

interface MoviesRepository {
    fun getMovies() : ArrayList<Movie>
}