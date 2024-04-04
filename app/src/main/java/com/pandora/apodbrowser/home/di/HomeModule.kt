package com.pandora.apodbrowser.home.di

import com.pandora.apodbrowser.di.AppModule
import com.pandora.apodbrowser.home.viewmodel.HomeViewModelFactory
import com.pandora.domain.usecases.FetchPaginatedPicsUsecase
import com.pandora.domain.usecases.FetchPicsUsecase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

@Module
object HomeModule {

    @Provides
    fun provideHomeViewModelFactory(
        fetchPicsUsecase: FetchPicsUsecase,
        fetchPaginatedPicsUsecase: FetchPaginatedPicsUsecase,
        @Named(AppModule.DEFAULT_BACKGROUND_COROUTINE_CONTEXT_KEY)
        backgroundCoroutineContext: CoroutineContext,
    ): HomeViewModelFactory =
        HomeViewModelFactory(
            fetchPicsUsecase,
            fetchPaginatedPicsUsecase,
            backgroundCoroutineContext
        )
}