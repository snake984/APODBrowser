package com.pandora.repositories.di

import com.pandora.api.Requester
import com.pandora.fetchpics.repositories.PicRepository
import com.pandora.repositories.PicRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object RepositoriesModule {

    @Provides
    fun providePicRepository(requester: Requester, @Named(Requester.APOD_API_KEY_DI_TAG) apodApiKey: String): PicRepository = PicRepositoryImpl(requester, apodApiKey)
}