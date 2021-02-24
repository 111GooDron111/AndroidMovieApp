package com.dmabram15.androidmovieapp.view

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import com.dmabram15.androidmovieapp.viewmodel.MainMoviesFragmentViewModel
import com.dmabram15.androidmovieapp.viewmodel.MovieCardAdapter
import com.dmabram15.androidmovieapp.viewmodel.OnMovieCardClickListener
import com.dmabram15.androidmoviesapp.databinding.MainFragmentBinding

class MainMoviesFragment : Fragment(), OnMovieCardClickListener {

    private lateinit var adapter: MovieCardAdapter

    private lateinit var binding: MainFragmentBinding

    companion object {
        fun newInstance() = MainMoviesFragment()
    }

    private val viewModel: MainMoviesFragmentViewModel by lazy {
        ViewModelProvider(this).get(MainMoviesFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)
        return binding.main
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeProperties()
    }

    private fun initializeProperties() {
        context?.let { setRecyclerViews(it, ArrayList(0)) }
        viewModel.getMovies().observe(viewLifecycleOwner, { renderMovies(it)})
    }

    //Настройка и отображение ресайклера
    private fun setRecyclerViews(context: Context, movies: ArrayList<Movie>) {

        adapter = MovieCardAdapter(context, movies, this)
        binding.recommendedRV.adapter = adapter
        binding.recommendedRV.layoutManager = LinearLayoutManager(context)
            .apply {
                orientation = RecyclerView.HORIZONTAL
            }
    }

    private fun renderMovies(movies: ArrayList<Movie>) {
        adapter.changeMovies(movies)
    }

    override fun onMovieCardClick(movie: Movie) {
        val bundle = Bundle()
        bundle.putParcelable(MovieInfoFragment.MOVIE_KEY, movie)
        val infoFragment = MovieInfoFragment.newInstance()
        infoFragment.arguments = bundle
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.container, infoFragment)
            ?.addToBackStack("")
            ?.commitAllowingStateLoss()

        (activity?.findViewById<View>(R.id.delimiterLineView))
            ?.snackBarShow("${movie.title} is showing")
    }
}
