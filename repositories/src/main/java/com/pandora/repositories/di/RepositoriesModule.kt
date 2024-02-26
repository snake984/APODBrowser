package com.pandora.repositories.di

import com.pandora.api.Requester
import com.pandora.fetchpics.repositories.PicRepository
import com.pandora.repositories.PicRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
object RepositoriesModule {

    @Provides
    fun providePicRepository(requester: Requester): PicRepository = PicRepositoryImpl(requester)
}