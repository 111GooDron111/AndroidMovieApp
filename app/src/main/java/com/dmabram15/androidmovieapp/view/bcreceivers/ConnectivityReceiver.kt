package com.dmabram15.androidmovieapp.view.bcreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ConnectivityReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Connectivity changed", Toast.LENGTH_SHORT).show()
    }
}