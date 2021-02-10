package com.dmabram15.androidmovieapp.view

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dmabram15.androidmoviesapp.R
import com.dmabram15.androidmovieapp.model.Movie
import com.dmabram15.androidmovieapp.viewmodel.MainViewModel
import com.dmabram15.androidmovieapp.viewmodel.OnMovieCardClickListener

class MainMoviesFragment : Fragment() {

    //TODO перенести инициализацию movies в viewModel    
    private var movies : ArrayList<Movie> = ArrayList(0)
    private lateinit var adapter : MovieCardAdapter
    private lateinit var recommendedRV : RecyclerView

    private var moviesObserver = Observer<ArrayList<Movie>>{ renderMovies(it)}

    companion object {
        fun newInstance() = MainMoviesFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeProperties()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recommendedRV = view.findViewById(R.id.recommendedRV)

        setRecyclerViews(view.context)
    }

    private fun initializeProperties() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getMovies().observe(viewLifecycleOwner, moviesObserver)
    }

    private fun setRecyclerViews(context: Context){
        adapter = MovieCardAdapter(context, movies)
        var onMovieCardClickListener : OnMovieCardClickListener = object : OnMovieCardClickListener{
            override fun onMovieCardClick(movie: Movie) {
                viewModel.clickOnMovie(movie)
            }
        }
        adapter.onMovieCardClickListener = onMovieCardClickListener
        recommendedRV.adapter = adapter
        var linearLayoutManager : LinearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        recommendedRV.layoutManager = linearLayoutManager
    }

    private fun renderMovies(it: ArrayList<Movie>) {
        movies = it
        adapter.changeMovies(movies)
    }

}