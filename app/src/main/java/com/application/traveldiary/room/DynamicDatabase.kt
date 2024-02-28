package com.application.traveldiary.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.application.traveldiary.models.Dynamic

@Database(entities = [Dynamic::class], version = 1)
abstract class DynamicDatabase : RoomDatabase() {
    abstract fun getDynamicDao(): DynamicDao
}