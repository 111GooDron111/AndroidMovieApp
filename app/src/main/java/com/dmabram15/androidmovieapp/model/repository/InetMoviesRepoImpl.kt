package com.dmabram15.androidmovieapp.model.repository

import com.dmabram15.androidmovieapp.api.MoviesAPI
import com.dmabram15.androidmovieapp.model.Movie
import com.dmabram15.androidmovieapp.view.TMDB_API_KEY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InetMoviesRepo : MoviesRepository {

    private val moviesService = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MoviesAPI::class.java)

    override fun getMovies(): ArrayList<Movie> {
        TODO("Not yet implemented")
    }

    override fun getMoviesForPeriod(period: String): ArrayList<Movie>? {
        val movies = moviesService.getRecommended(period, TMDB_API_KEY, "en-US")
            .execute()
            .body()?.movies
        return movies
    }
}