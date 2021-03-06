package com.dmabram15.androidmovieapp.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.dmabram15.androidmoviesapp.R
import com.dmabram15.androidmovieapp.model.Movie
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso
import java.io.BufferedInputStream

class MovieCardAdapter(
    private val context: Context?,
    private val onMovieCardClickListener: OnMovieCardClickListener
) : RecyclerView.Adapter<MovieCardAdapter.MovieCardViewHolder>() {

    private val movies: java.util.ArrayList<Movie> = ArrayList(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCardViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.movie_card,
            parent, false
        )
        return MovieCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieCardViewHolder, position: Int) {
        setImage(holder, position)
        setTitle(holder, position)

        //Привязываем обработчик view
        holder.movieItem.setOnClickListener {
            onMovieCardClickListener.onMovieCardClick(movies[position])
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    // По пути получает баннер и устанавливает в ImageView
    private fun setImage(cardViewHolder: MovieCardViewHolder, position: Int) {
        val name = movies[position].poster_path
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w500/$name")
            .into(cardViewHolder.imageView)
    }

    private fun setTitle(cardViewHolder: MovieCardViewHolder, position: Int) {
        cardViewHolder.materialTextView.text = movies[position].title
    }

    fun changeMovies(movies: ArrayList<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    class MovieCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.movieBannerImView)
        var materialTextView: MaterialTextView = itemView.findViewById(R.id.movieTitleTextView)
        var movieItem: ConstraintLayout = itemView.findViewById(R.id.movieItem)
    }
}