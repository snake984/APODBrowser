package com.pandora.apodbrowser.home.di

import com.pandora.apodbrowser.home.viewmodel.HomeViewModelFactory
import com.pandora.fetchpics.usecases.FetchPaginatedPicsUsecase
import com.pandora.fetchpics.usecases.FetchPicsUsecase
import dagger.Module
import dagger.Provides

@Module
object HomeModule {

    @Provides
    fun provideHomeViewModelFactory(
        fetchPicsUsecase: FetchPicsUsecase,
        fetchPaginatedPicsUsecase: FetchPaginatedPicsUsecase
    ): HomeViewModelFactory =
        HomeViewModelFactory(fetchPicsUsecase, fetchPaginatedPicsUsecase)
}