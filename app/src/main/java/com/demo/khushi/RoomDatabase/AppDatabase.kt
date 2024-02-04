package com.demo.khushi.RoomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demo.khushi.Models.MovieEntity
import com.demo.khushi.interfaces.MovieDao

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}
