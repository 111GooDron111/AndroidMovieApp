package com.dmabram15.androidmovieapp.model.repository

import com.dmabram15.androidmovieapp.api.MoviesAPI
import com.dmabram15.androidmovieapp.model.MoviesDTO
import com.dmabram15.androidmovieapp.view.TMDB_API_KEY
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class MoviesInternetRepositoryImpl : MoviesInternetRepository {

    private val moviesService = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .client(createOkHttpClient(MovieApiInterceptor()))
        .build()
        .create(MoviesAPI::class.java)

    override fun getMoviesForPeriod(period: String, callback: Callback<MoviesDTO>) {
        moviesService.getTrending(period, TMDB_API_KEY, "en-US").enqueue(callback)
    }

    override fun getMoviesByGenres(isAdult: Boolean, genres: String, callback: Callback<MoviesDTO>) {
        moviesService.getByGenres(
            TMDB_API_KEY,
            "en-US",
            MoviesAPI.SORT_POPULARITY,
            isAdult,
            genres
        ).enqueue(callback)
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.let {
            it.addInterceptor(interceptor)
            it.callTimeout(10000, TimeUnit.MILLISECONDS)
        }
        return httpClient.build()
    }

    inner class MovieApiInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            return chain.proceed(chain.request())
        }
    }

}