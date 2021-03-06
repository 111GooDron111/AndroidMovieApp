package com.dmabram15.androidmovieapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class MoviesDTO(
    @SerializedName("results") var movies: ArrayList<Movie>?
) : Parcelable