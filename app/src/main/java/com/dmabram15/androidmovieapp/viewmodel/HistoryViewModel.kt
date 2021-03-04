package com.dmabram15.androidmovieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmabram15.App
import com.dmabram15.androidmovieapp.model.repository.LocalRepositoryImpl

class HistoryViewModel : ViewModel() {

    private var localeRepository = LocalRepositoryImpl(App.getHistoryDAO())

    fun getHistoryMovie() = historyLifeData

    private val historyLifeData : MutableLiveData<AppState> by lazy {
        MutableLiveData<AppState>()
    }

    fun getAllHistory() {
        Thread{
            val movies = localeRepository.getAllHistory()
            movies.let {
                historyLifeData.postValue(AppState.Success(movies))
            }
        }.start()
    }
}