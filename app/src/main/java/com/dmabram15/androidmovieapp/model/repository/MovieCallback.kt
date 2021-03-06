package com.dmabram15.androidmovieapp.model.repository

import androidx.lifecycle.MutableLiveData
import com.dmabram15.androidmovieapp.model.MoviesDTO
import com.dmabram15.androidmovieapp.viewmodel.AppState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieCallback(
    private val genre : String,
    private val liveMovieToUpdate: MutableMap<String, MutableLiveData<AppState>>
) : Callback<MoviesDTO> {

    override fun onResponse(call: Call<MoviesDTO>, response: Response<MoviesDTO>) {
        val serverResponse : MoviesDTO? = response.body()
        liveMovieToUpdate[genre]?.postValue(
            if (response.isSuccessful && serverResponse != null){
                getCheckedResponse(serverResponse)                                            //https://api.themoviedb.org/3/discover/movie?api_key=40a98f3e5739954b908f37352fdfb0d1&language=en-US&sort_by=popularity.desc&include_adult=true&genres=fantasy
            }
            else AppState.Error(Throwable("Данные не получены"))
        )
    }

    override fun onFailure(call: Call<MoviesDTO>, t: Throwable) {
        liveMovieToUpdate[genre]?.postValue(AppState.Error(Throwable("Данные не получены")))
    }

    @Suppress("NullChecksToSafeCall")
    private fun getCheckedResponse(serverResponse: MoviesDTO): AppState {
        return if (serverResponse.movies == null) {
            AppState.Error(Throwable("Данные не получены"))
        } else AppState.Success(serverResponse.movies!!, genre)
    }
}