package com.pandora.fetchpics.di

import com.pandora.fetchpics.repositories.PicRepository
import com.pandora.fetchpics.usecases.FetchPicsUsecase
import com.pandora.fetchpics.usecases.FetchPicsUsecaseImpl
import dagger.Module
import dagger.Provides

@Module
object FetchPicsModule {

    @Provides
    fun provideFetchPicsUsecase(picRepository: PicRepository): FetchPicsUsecase =
        FetchPicsUsecaseImpl(picRepository)
}