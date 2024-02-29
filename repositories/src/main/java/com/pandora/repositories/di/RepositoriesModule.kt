package com.pandora.repositories.di

import androidx.paging.PagingSource
import com.pandora.api.Requester
import com.pandora.fetchpics.model.PicOfTheDay
import com.pandora.fetchpics.repositories.PicRepository
import com.pandora.fetchpics.usecases.PagingKey
import com.pandora.repositories.PicRepositoryImpl
import com.pandora.repositories.paging.PicPagingSource
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object RepositoriesModule {

    @Provides
    fun providePicRepository(
        requester: Requester,
        @Named(Requester.APOD_API_KEY_DI_TAG) apodApiKey: String
    ): PicRepository = PicRepositoryImpl(requester, apodApiKey)

    @Provides
    fun providePicPagingSource(picRepository: PicRepository): PagingSource<PagingKey, PicOfTheDay> =
        PicPagingSource(picRepository)
}