package com.dmabram15.androidmovieapp.model

data class Movie(var title  :String, var descriptionMovie : String, var assetPath : String){
    override fun toString(): String {
        return title
    }
}