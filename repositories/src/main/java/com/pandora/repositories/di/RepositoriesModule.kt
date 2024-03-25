package com.pandora.repositories.di

import androidx.paging.PagingSource
import com.pandora.api.Requester
import com.pandora.domain.model.PicOfTheDay
import com.pandora.domain.repositories.PicRepository
import com.pandora.domain.usecases.PagingKey
import com.pandora.repositories.PicRepositoryImpl
import com.pandora.repositories.paging.PicPagingSource
import com.pandora.storage.database.dao.FavoritePicsDao
import com.pandora.storage.filesystem.FileManager
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object RepositoriesModule {

    @Provides
    fun providePicRepository(
        favoritePicsDao: FavoritePicsDao,
        fileManager: FileManager,
        requester: Requester,
        @Named(Requester.APOD_API_KEY_DI_TAG) apodApiKey: String
    ): PicRepository = PicRepositoryImpl(favoritePicsDao, fileManager, requester, apodApiKey)

    @Provides
    fun providePicPagingSource(picRepository: PicRepository): PagingSource<PagingKey, PicOfTheDay> =
        PicPagingSource(picRepository)
}