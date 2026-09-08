package com.pandora.storage.di

import android.content.Context
import androidx.room.Room
import com.pandora.storage.database.APODBrowserDatabase
import com.pandora.storage.database.dao.FavoritePicsDao
import com.pandora.storage.filesystem.FileManager
import com.pandora.storage.filesystem.MediaStoreFileManager
import org.koin.dsl.module

val storageModule = module {
    single<APODBrowserDatabase> {
        Room.databaseBuilder(
            get<Context>(),
            APODBrowserDatabase::class.java,
            "apod_browser_db"
        ).fallbackToDestructiveMigration().build()
    }
    factory<FavoritePicsDao> { get<APODBrowserDatabase>().picOfTheDayDao() }
    single<FileManager> { MediaStoreFileManager(get()) }
}