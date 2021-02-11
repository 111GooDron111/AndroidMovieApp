package com.dmabram15.androidmovieapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    var title: String,
    var descriptionMovie: String,
    var assetPath: String
) : Parcelable