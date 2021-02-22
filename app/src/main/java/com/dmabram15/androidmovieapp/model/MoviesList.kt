package com.dmabram15.androidmovieapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
class MoviesList (var results : ArrayList<Movie>) : Parcelable