package com.pandora.fetchpics.di

import androidx.paging.PagingSource
import com.pandora.fetchpics.model.PicOfTheDay
import com.pandora.fetchpics.repositories.PicRepository
import com.pandora.fetchpics.usecases.FetchPaginatedPicsUsecase
import com.pandora.fetchpics.usecases.FetchPaginatedPicsUsecaseImpl
import com.pandora.fetchpics.usecases.FetchPicsUsecase
import com.pandora.fetchpics.usecases.FetchPicsUsecaseImpl
import com.pandora.fetchpics.usecases.PagingKey
import dagger.Module
import dagger.Provides

@Module
object FetchPicsModule {

    @Provides
    fun provideFetchPicsUsecase(picRepository: PicRepository): FetchPicsUsecase =
        FetchPicsUsecaseImpl(picRepository)

    @Provides
    fun provideFetchPaginatedPicsUsecase(pagingSource: PagingSource<PagingKey, PicOfTheDay>): FetchPaginatedPicsUsecase =
        FetchPaginatedPicsUsecaseImpl(pagingSource)
}