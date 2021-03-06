package com.dmabram15.androidmovieapp.model.repository

import com.dmabram15.androidmovieapp.model.Movie
import com.dmabram15.androidmovieapp.model.database.HistoryDAO
import com.dmabram15.androidmovieapp.model.database.HistoryEntity

class LocalRepositoryImpl(private val localDataSource : HistoryDAO) : LocalRepository {

    override fun getAllHistory(): List<Movie> {
        return convertHistoryEntityToMovies(localDataSource.all())
    }

    override fun saveEntity(movie: Movie) {
        localDataSource.insert(convertMovieToHistoryEntity(movie))
    }

    private fun convertMovieToHistoryEntity(movie: Movie): HistoryEntity {
        return HistoryEntity(0, movie.id, movie.title, movie.overview, movie.poster_path)
    }

    private fun convertHistoryEntityToMovies(entityList: List<HistoryEntity>): List<Movie> {
        return entityList.map {
            Movie(it.internetId, it.title, it.overview, it.posterPath)
        }
    }
}