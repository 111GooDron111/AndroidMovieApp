package com.dmabram15.androidmovieapp.view.services

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.dmabram15.androidmovieapp.model.MoviesList
import com.dmabram15.androidmovieapp.view.*
import com.google.gson.GsonBuilder
import java.io.*
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class InternetLoadService : JobIntentService() {

    private val languageKey = "en-US"
    private val urlRequest = URL("https://api.themoviedb.org/3/trending/movie/day?" +
            "api_key=$TMDB_API_KEY" +
            "&language=$languageKey")

    override fun onHandleWork(intent: Intent) {
        val response = getResponce(urlRequest)
        val moviesList = GsonBuilder().create().fromJson(response, MoviesList::class.java)
        val getMoviesintent  = Intent(MOVIES_LIST_INTENT_FILTER).apply {
            putExtra(MOVIES_EXTRA_KEY, moviesList)
        }
        sendBroadcast(getMoviesintent)
        response.close()
    }

    companion object {
        fun start(context: Context, intent: Intent) {
            enqueueWork(context, InternetLoadService::class.java, 101, intent)
        }
    }

    fun getResponce(urlRequest : URL) : Reader{
        val httpsConnection =  urlRequest.openConnection() as HttpsURLConnection
        httpsConnection.apply {
            connectTimeout = 10000
        }

        val bufferedReader = BufferedReader(InputStreamReader(httpsConnection.inputStream))
        return bufferedReader
    }
}