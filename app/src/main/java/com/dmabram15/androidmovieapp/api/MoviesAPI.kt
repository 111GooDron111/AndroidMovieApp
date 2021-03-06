package com.dmabram15.androidmovieapp.api

import com.dmabram15.androidmovieapp.model.MoviesDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {

    companion object {
        const val DAY = "day"
        const val WEEK = "week"
        const val LANGUAGE_EN = "EN-us"
        const val SORT_POPULARITY = "popularity.desc"

        //Различные жанры
        const val FANTASY_GENRES = "14"
        const val ACTION_GENRES = "28"
        const val COMEDY_GENRES = "35"
        const val FAMILY_GENRES = "10751"
    }

    @GET("trending/movie/{period}")
    fun getTrending(
        @Path ("period") period : String,
        @Query("api_key") key : String,
        @Query("language") locale : String
    ) : Call<MoviesDTO>

    @GET("discover/movie")
    fun getByGenres (
        @Query("api_key") key : String,
        @Query("language") locale : String,
        @Query("sort_by") sort : String,
        @Query("include_adult") isAdult : Boolean,
        @Query("with_genres") genres : String,
    ) : Call<MoviesDTO>
}