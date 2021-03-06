package com.dmabram15.androidmovieapp.model.repository

import com.dmabram15.androidmovieapp.model.Movie

interface LocalRepository {
    fun getAllHistory(): List<Movie>

    fun saveEntity(movie: Movie)
}