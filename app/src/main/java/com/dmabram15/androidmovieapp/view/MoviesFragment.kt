package com.dmabram15.androidmovieapp.view

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dmabram15.androidmoviesapp.R
import com.dmabram15.androidmovieapp.model.Movie
import com.dmabram15.androidmovieapp.viewmodel.AppState
import com.dmabram15.androidmovieapp.viewmodel.MoviesFragmentViewModel
import com.dmabram15.androidmovieapp.viewmodel.MovieCardAdapter
import com.dmabram15.androidmovieapp.viewmodel.OnMovieCardClickListener
import com.dmabram15.androidmoviesapp.databinding.MoviesFragmentBinding

class MoviesFragment : Fragment(), OnMovieCardClickListener {

    private lateinit var adapter: MovieCardAdapter

    private lateinit var binding: MoviesFragmentBinding

    companion object {
        fun newInstance() = MoviesFragment()
    }

    private val viewModel: MoviesFragmentViewModel by lazy {
        ViewModelProvider(this).get(MoviesFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MoviesFragmentBinding.inflate(inflater)
        return binding.main
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeProperties()

        viewModel.getMovieFromData()
    }

    private fun initializeProperties() {
        context?.let { setRecyclerViews(it, ArrayList(0)) }
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderMovies(it) })
    }

    private fun setRecyclerViews(context: Context, movies: ArrayList<Movie>) {
        adapter = MovieCardAdapter(context, movies, this)
        binding.recommendedRV.adapter = adapter
        binding.recommendedRV.layoutManager = LinearLayoutManager(context)
            .apply {
                orientation = RecyclerView.HORIZONTAL
            }
    }

    private fun renderMovies(appState: AppState) {
        when(appState) {
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapter.changeMovies(appState.moviesData as ArrayList<Movie>)
            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                binding.main.showSnackbar(appState.error.message.toString())
            }
        }
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

        viewModel.saveMovieToDb(movie)

        (activity?.findViewById<View>(R.id.delimiterLineView))
            ?.showSnackbar("${movie.title} is showing")
    }
}
