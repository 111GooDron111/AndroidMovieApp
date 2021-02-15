package com.dmabram15.androidmovieapp.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.dmabram15.androidmoviesapp.R
import com.dmabram15.androidmovieapp.model.Movie
import com.dmabram15.androidmovieapp.viewmodel.MovieInfoFragmentViewModel
import java.io.BufferedInputStream

class MovieInfoFragment : Fragment() {
    companion object {
        val MOVIE_KEY = "movie"
        fun newInstance() = MovieInfoFragment()
    }

    private lateinit var selectedMovie: Movie
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var bitmapBanner: ImageView

    private val viewModel: MovieInfoFragmentViewModel by lazy {
        ViewModelProvider(this).get(MovieInfoFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = view.findViewById(R.id.titleMovieInfoTextView)
        description = view.findViewById(R.id.descriptionInfoTextView)
        bitmapBanner = view.findViewById(R.id.bannerInfoImView)

        selectedMovie = ((arguments?.getParcelable<Movie>(MOVIE_KEY)) as Movie)
        showMovie(selectedMovie)
    }

    private fun showMovie(it: Movie?) {
        title.text = it?.title
        description.text = it?.descriptionMovie
        bitmapBanner.setImageBitmap(getBitmap(it?.assetPath))
    }

    private fun getBitmap(assetPath: String?): Bitmap {
        val reader = BufferedInputStream(context?.assets?.open(assetPath.toString()))
        val bitmap: Bitmap = BitmapFactory.decodeStream(reader)
        reader.close()
        return bitmap
    }
}