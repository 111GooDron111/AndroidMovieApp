package com.dmabram15.androidmovieapp.view

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(message: String) {
    Snackbar
        .make(this, message, Snackbar.LENGTH_SHORT)
        .show()
}

