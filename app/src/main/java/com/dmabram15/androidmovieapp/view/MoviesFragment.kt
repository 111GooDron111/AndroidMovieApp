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
import com.dmabram15.androidmovieapp.api.MoviesAPI
import com.dmabram15.androidmoviesapp.R
import com.dmabram15.androidmovieapp.model.Movie
import com.dmabram15.androidmovieapp.viewmodel.AppState
import com.dmabram15.androidmovieapp.viewmodel.MoviesFragmentViewModel
import com.dmabram15.androidmovieapp.viewmodel.MovieCardAdapter
import com.dmabram15.androidmovieapp.viewmodel.OnMovieCardClickListener
import com.dmabram15.androidmoviesapp.databinding.MoviesFragmentBinding

class MoviesFragment : Fragment(), OnMovieCardClickListener {

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

        viewModel.getMovieFromInternet()
    }

    private fun initializeProperties() {
        setRecyclerViews()
        viewModel.liveTrending.observe(viewLifecycleOwner, { renderMovies(it) })
        viewModel.liveAction.observe(viewLifecycleOwner, { renderMovies(it) })
        viewModel.liveComedy.observe(viewLifecycleOwner, { renderMovies(it) })
        viewModel.liveFamily.observe(viewLifecycleOwner, { renderMovies(it) })
        viewModel.liveFantasy.observe(viewLifecycleOwner, { renderMovies(it) })
    }

    private fun setRecyclerViews() {
        context?.let { setRecyclerView(it, binding.recommendedRV) }
        context?.let { setRecyclerView(it, binding.actionRV) }
        context?.let { setRecyclerView(it, binding.comedyRV) }
        context?.let { setRecyclerView(it, binding.familyRV) }
        context?.let { setRecyclerView(it, binding.fantasyRV) }
    }

    private fun setRecyclerView(context: Context, recyclerView: RecyclerView) {
        val adapterRecyclerView = MovieCardAdapter(context, this)
        recyclerView.adapter = adapterRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
            .apply {
                orientation = RecyclerView.HORIZONTAL
            }
    }

    private fun renderMovies(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                renderMoviesRecycler(appState)
            }
            is AppState.Loading -> {

            }
            is AppState.Error -> {
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

        binding.main.showSnackbar("${movie.title} is showing")
    }

    @Synchronized
    private fun renderMoviesRecycler(appState: AppState.Success) {
        when (appState.genreListName) {
            MoviesAPI.ACTION_GENRES -> {
                setMovieToAdapter(binding.actionRV.adapter, appState)
            }
            MoviesAPI.COMEDY_GENRES -> {
                setMovieToAdapter(binding.comedyRV.adapter, appState)
            }
            MoviesAPI.FAMILY_GENRES -> {
                setMovieToAdapter(binding.familyRV.adapter, appState)
            }
            MoviesAPI.FANTASY_GENRES -> {
                setMovieToAdapter(binding.fantasyRV.adapter, appState)
            }
            else -> {
                setMovieToAdapter(binding.recommendedRV.adapter, appState)
            }
        }
    }

    private fun setMovieToAdapter(
        adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?,
        appState: AppState.Success
    ) {
        (adapter as MovieCardAdapter)
            .changeMovies(appState.moviesData as ArrayList<Movie>)
    }
}
