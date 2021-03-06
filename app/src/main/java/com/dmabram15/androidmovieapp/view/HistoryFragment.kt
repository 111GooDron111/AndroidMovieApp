package com.dmabram15.androidmovieapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmabram15.androidmovieapp.model.Movie
import com.dmabram15.androidmovieapp.viewmodel.AppState
import com.dmabram15.androidmovieapp.viewmodel.HistoryAdapter
import com.dmabram15.androidmovieapp.viewmodel.HistoryViewModel
import com.dmabram15.androidmoviesapp.databinding.HistoryFragmentBinding

class HistoryFragment : Fragment() {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var binding : HistoryFragmentBinding

    private lateinit var viewModel: HistoryViewModel

    private val historyAdapter : HistoryAdapter by lazy {
        HistoryAdapter(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HistoryFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        viewModel.getHistoryMovie().observe(this, {renderMovies(it)})

        initializeRecycler()

        viewModel.getAllHistory()
    }

    private fun initializeRecycler() {
        binding.historyRecycler.apply {
            adapter = historyAdapter
            val historyLayoutManager = LinearLayoutManager(context)
            historyLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = historyLayoutManager
        }
    }

    private fun renderMovies(appState: AppState) {
        when(appState) {
            is AppState.Success -> {
                historyAdapter.setMovies( ArrayList(appState.moviesData))
            }
            is AppState.Loading -> {

            }
            is AppState.Error -> {
                binding.root.showSnackbar(appState.error.message.toString())
            }
        }
    }
}