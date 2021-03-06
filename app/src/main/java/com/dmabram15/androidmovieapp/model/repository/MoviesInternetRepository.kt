package com.dmabram15.androidmovieapp.model.repository

import com.dmabram15.androidmovieapp.model.MoviesDTO
import retrofit2.Callback

interface MoviesInternetRepository {
    fun getMoviesForPeriod(period: String, callback: Callback<MoviesDTO>)

    fun getMoviesByGenres(isAdult: Boolean, genres : String, callback: Callback<MoviesDTO>)
}