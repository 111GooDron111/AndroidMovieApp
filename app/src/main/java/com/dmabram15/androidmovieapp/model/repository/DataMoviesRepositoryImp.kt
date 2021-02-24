package com.dmabram15.androidmovieapp.model.repository

import com.dmabram15.androidmovieapp.model.Movie
import java.io.File
import java.net.URL

class DataMoviesRepositoryImp() : MoviesRepository {

    private val pathPrefix: String = "imagecache/"

    private val movies = arrayListOf(
        Movie(
            0,"Stranger Things", "BlaBLaBla...",
            "${pathPrefix}stranger_things.jpg"
        ),
        Movie(
            1,"Tenet", "Tenet. The movie is ...",
            "${pathPrefix}dovod.jpg"
        ),
        Movie(
            2,"Empty man", "Empty man. The movie is ...",
            "${pathPrefix}empty_man.jpg"
        ),
        Movie(
            3,"Witches", "Witches. The movie is ...",
            "${pathPrefix}witches.jpg"
        ),
        Movie(
            4,"The Secret: Dare to Dream", "The Secret. The movie is ...",
            "${pathPrefix}secret.jpg"
        ),
        Movie(
            5,"The Call of the Wild", "The Call of the Wild. The movie is ...",
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