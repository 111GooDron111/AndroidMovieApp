package com.dmabram15.androidmovieapp.model

import java.io.File
import java.net.URL

class DataMoviesRepositoryImp() : MoviesRepository {

    private val pathPrefix: String = "imagecache/"

    private val movies = arrayListOf(
        Movie(
            "Stranger Things", "BlaBLaBla...",
            "${pathPrefix}stranger_things.jpg"
        ),
        Movie(
            "Tenet", "Tenet. The movie is ...",
            "${pathPrefix}dovod.jpg"
        ),
        Movie(
            "Empty man", "Empty man. The movie is ...",
            "${pathPrefix}empty_man.jpg"
        ),
        Movie(
            "Witches", "Witches. The movie is ...",
            "${pathPrefix}witches.jpg"
        ),
        Movie(
            "The Secret: Dare to Dream", "The Secret. The movie is ...",
            "${pathPrefix}secret.jpg"
        ),
        Movie(
            "The Call of the Wild", "The Call of the Wild. The movie is ...",
            "${pathPrefix}call.jpg"
        )
    )

    override fun getMovies(): ArrayList<Movie> {
        return (movies)
    }

    private fun getUrl(path: String): URL {
        return File(path).toURI().toURL()
    }
}