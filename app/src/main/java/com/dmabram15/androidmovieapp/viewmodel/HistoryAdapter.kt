package com.dmabram15.androidmovieapp.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dmabram15.androidmovieapp.model.Movie
import com.dmabram15.androidmovieapp.view.showSnackbar
import com.dmabram15.androidmoviesapp.R

class HistoryAdapter(private val context: Context?) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var movies : ArrayList<Movie> = ArrayList(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.history_card,
            parent,
            false
        )
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.title.text = movies[position].title

        holder.item.setOnClickListener {
            it.showSnackbar(movies[position].title)
        }
    }

    override fun getItemCount(): Int {
        return movies.count()
    }

    fun setMovies(moviesFromDB : ArrayList<Movie>){
        moviesFromDB.let {
            movies.clear()
            movies.addAll(moviesFromDB)
            notifyDataSetChanged()
        }
    }

    class HistoryViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val item = itemView.findViewById<LinearLayout>(R.id.history_item)
        val title = itemView.findViewById<TextView>(R.id.history_title)
    }
}