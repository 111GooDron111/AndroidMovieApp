package com.dmabram15.androidmovieapp.view.services

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService

class InternetLoadService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        //TODO реализовать выход в интернет и получение информации
        println("in service")
    }

    companion object {
        fun start(context: Context, intent: Intent) {
            enqueueWork(context, InternetLoadService::class.java, 101, intent)
        }
    }
}