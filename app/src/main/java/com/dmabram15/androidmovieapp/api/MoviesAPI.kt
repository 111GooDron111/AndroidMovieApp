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
    }

    @GET("trending/movie/{period}")
    fun getRecommended(
        @Path ("period") period : String,
        @Query("api_key") key : String,
        @Query("language") locale : String
    ) : Call<MoviesDTO>

}