package com.dmabram15.androidmovieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmabram15.App
import com.dmabram15.androidmovieapp.api.MoviesAPI
import com.dmabram15.androidmovieapp.model.Movie
import com.dmabram15.androidmovieapp.model.repository.MoviesInternetRepositoryImpl
import com.dmabram15.androidmovieapp.model.repository.LocalRepositoryImpl
import com.dmabram15.androidmovieapp.model.repository.MovieCallback
import com.dmabram15.androidmovieapp.model.repository.MoviesInternetRepository

class MoviesFragmentViewModel() : ViewModel() {

    val liveTrending = MutableLiveData<AppState>()
    val liveAction = MutableLiveData<AppState>()
    val liveComedy = MutableLiveData<AppState>()
    val liveFamily = MutableLiveData<AppState>()
    val liveFantasy = MutableLiveData<AppState>()

    private val liveMovieToUpdate = mutableMapOf<String, MutableLiveData<AppState>>(
        Pair("", liveTrending),
        Pair(MoviesAPI.ACTION_GENRES, liveAction),
        Pair(MoviesAPI.COMEDY_GENRES, liveComedy),
        Pair(MoviesAPI.FAMILY_GENRES, liveFamily),
        Pair(MoviesAPI.FANTASY_GENRES, liveFantasy)
    )

    private val moviesRepository: MoviesInternetRepository = MoviesInternetRepositoryImpl()
    private val localRepositoryImpl : LocalRepositoryImpl = LocalRepositoryImpl(App.getHistoryDAO())

    fun getMovieFromInternet() {
        getTrendingMovie()
        getMovieByGenres(MoviesAPI.ACTION_GENRES)
        getMovieByGenres(MoviesAPI.COMEDY_GENRES)
        getMovieByGenres(MoviesAPI.FAMILY_GENRES)
        getMovieByGenres(MoviesAPI.FANTASY_GENRES)
    }

    private fun getTrendingMovie() {
        val callback = MovieCallback("", liveMovieToUpdate)
        moviesRepository.getMoviesForPeriod("day", callback)
    }

    private fun getMovieByGenres(genres: String) {
        val callback = MovieCallback(genres, liveMovieToUpdate)
        moviesRepository.getMoviesByGenres(false, genres, callback)
    }

    fun saveMovieToDb(movie : Movie) {
        Thread{
            localRepositoryImpl.saveEntity(movie)
        }.start()
    }

    fun getLiveData() = liveMovieToUpdate

}