package com.dmabram15.androidmovieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmabram15.androidmovieapp.model.MoviesDTO
import com.dmabram15.androidmovieapp.model.repository.InetMoviesRepo
import com.dmabram15.androidmovieapp.model.repository.MoviesInternetRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesFragmentViewModel() : ViewModel() {

    private val liveMovieToUpdate = MutableLiveData<AppState>()
    private val moviesRepository: MoviesInternetRepository = InetMoviesRepo()

    fun getMovieFromData() {
        moviesRepository.getMoviesForPeriod("day", callback)
    }

    private val callback = object : Callback<MoviesDTO> {
        override fun onResponse(call: Call<MoviesDTO>, response: Response<MoviesDTO>) {
            val serverResponse : MoviesDTO? = response.body()
            liveMovieToUpdate.postValue(
                if (response.isSuccessful && serverResponse != null){
                    getCheckedResponce(serverResponse)
                }
            else AppState.Error(Throwable("Данные не получены"))
            )
        }

        override fun onFailure(call: Call<MoviesDTO>, t: Throwable) {
            liveMovieToUpdate.postValue(AppState.Error(Throwable("Данные не получены")))
        }
    }

    fun getLiveData() = liveMovieToUpdate

    @Suppress("NullChecksToSafeCall")
    private fun getCheckedResponce(serverResponse: MoviesDTO): AppState {
        if (serverResponse.movies == null) {
            return AppState.Error(Throwable("Данные не получены"))
        }
        else return AppState.Success(serverResponse.movies!!)
    }
}