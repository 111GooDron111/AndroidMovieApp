package com.dmabram15.androidmovieapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    var id : Int,
    val title: String,
    var overview: String,
    var poster_path: String
) : Parcelable