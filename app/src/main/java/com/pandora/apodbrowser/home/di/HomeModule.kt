package com.pandora.apodbrowser.home.di

import com.pandora.apodbrowser.home.viewmodel.HomeViewModelFactory
import com.pandora.fetchpics.usecases.FetchPicsUsecase
import dagger.Module
import dagger.Provides

@Module
object HomeModule {

    @Provides
    fun provideHomeViewModelFactory(fetchPicsUsecase: FetchPicsUsecase): HomeViewModelFactory =
        HomeViewModelFactory(fetchPicsUsecase)
}