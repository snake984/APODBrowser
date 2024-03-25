package com.pandora.storage.di

import android.content.Context
import androidx.room.Room
import com.pandora.storage.database.APODBrowserDatabase
import com.pandora.storage.database.dao.FavoritePicsDao
import com.pandora.storage.filesystem.AppFileManager
import com.pandora.storage.filesystem.FileManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object StorageModule {
    @Provides
    @Singleton
    fun provideDatabase(applicationContext: Context): APODBrowserDatabase =
        Room.databaseBuilder(
            applicationContext,
            APODBrowserDatabase::class.java,
            "apod_browser_db"
        ).build()

    @Provides
    fun providePicOfTheDayDao(database: APODBrowserDatabase): FavoritePicsDao =
        database.picOfTheDayDao()

    @Provides
    fun provideAppFileManager(applicationContext: Context): FileManager =
        AppFileManager(applicationContext.filesDir)
}