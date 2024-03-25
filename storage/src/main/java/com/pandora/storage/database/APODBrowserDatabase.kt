package com.pandora.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pandora.storage.database.dao.FavoritePicsDao
import com.pandora.storage.database.entities.FavoritePicEntity

@Database(entities = [FavoritePicEntity::class], version = 1)
abstract class APODBrowserDatabase: RoomDatabase() {
    abstract fun picOfTheDayDao(): FavoritePicsDao
}