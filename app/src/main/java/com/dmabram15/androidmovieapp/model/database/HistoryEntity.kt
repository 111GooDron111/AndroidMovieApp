package com.dmabram15.androidmovieapp.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val internetId : Int,
    val title : String,
    val overview : String,
    val posterPath : String
)