package com.dmabram15.androidmovieapp.model

import java.io.File
import java.net.URL

class DataMoviesRepositoryImp() : MoviesRepository {

    private val pathPrefix: String = "imagecache/"

    private val movies = ArrayList<Movie>(0)

    init {
        movies.add(Movie("Stranger Things", "BlaBLaBla...",
                "${pathPrefix}stranger_things.jpg"))
        movies.add(Movie("Tenet", "Tenet. The movie is ...",
                "${pathPrefix}dovod.jpg"))
        movies.add(Movie("Empty man", "Empty man. The movie is ...",
                "${pathPrefix}empty_man.jpg"))
        movies.add(Movie("Witches", "Witches. The movie is ...",
                "${pathPrefix}witches.jpg"))
        movies.add(Movie("The Secret: Dare to Dream", "The Secret. The movie is ...",
                "${pathPrefix}secret.jpg"))
        movies.add(Movie("The Call of the Wild", "The Call of the Wild. The movie is ...",
                "${pathPrefix}call.jpg"))
    }

    override fun getMovies(): ArrayList<Movie> {
        return movies
    }

    private fun getUrl(path: String): URL {
        return File(path).toURI().toURL()
    }
}