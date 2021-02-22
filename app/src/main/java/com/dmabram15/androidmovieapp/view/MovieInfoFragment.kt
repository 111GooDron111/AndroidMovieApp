package com.dmabram15.androidmovieapp.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dmabram15.androidmovieapp.model.Movie
import com.dmabram15.androidmovieapp.viewmodel.MovieInfoFragmentViewModel
import com.dmabram15.androidmoviesapp.databinding.MovieInfoFragmentBinding
import com.squareup.picasso.Picasso
import java.io.BufferedInputStream

class MovieInfoFragment : Fragment() {
    companion object {
        val MOVIE_KEY = "movie"
        fun newInstance() = MovieInfoFragment()
    }

    private lateinit var binding: MovieInfoFragmentBinding

    private lateinit var selectedMovie: Movie

    private val viewModel: MovieInfoFragmentViewModel by lazy {
        ViewModelProvider(this).get(MovieInfoFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieInfoFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedMovie = ((arguments?.getParcelable<Movie>(MOVIE_KEY)) as Movie)
        showMovie(selectedMovie)
    }

    private fun showMovie(it: Movie?) {
        binding.titleMovieInfoTextView.text = it?.title
        binding.descriptionInfoTextView.text = it?.overview

        Picasso.get()
            .load("https://image.tmdb.org/t/p/w500/${it?.poster_path}")
            .into(binding.bannerInfoImView)
    }

    private fun getBitmap(assetPath: String?): Bitmap {
        val reader = BufferedInputStream(context?.assets?.open(assetPath.toString()))
        val bitmap: Bitmap = BitmapFactory.decodeStream(reader)
        reader.close()
        return bitmap
    }
}