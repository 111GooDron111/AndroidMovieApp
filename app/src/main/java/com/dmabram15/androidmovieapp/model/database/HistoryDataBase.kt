package com.dmabram15.androidmovieapp.model.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1, exportSchema = false)
abstract class HistoryDataBase : RoomDatabase() {
        abstract fun historyDao() : HistoryDAO
}