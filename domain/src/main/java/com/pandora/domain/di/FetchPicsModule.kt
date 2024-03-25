package com.pandora.domain.di

import androidx.paging.PagingSource
import com.pandora.domain.model.PicOfTheDay
import com.pandora.domain.repositories.PicRepository
import com.pandora.domain.usecases.FetchFavoritePicsUsecase
import com.pandora.domain.usecases.FetchFavoritePicsUsecaseImpl
import com.pandora.domain.usecases.FetchPaginatedPicsUsecase
import com.pandora.domain.usecases.FetchPaginatedPicsUsecaseImpl
import com.pandora.domain.usecases.FetchPicsUsecase
import com.pandora.domain.usecases.FetchPicsUsecaseImpl
import com.pandora.domain.usecases.PagingKey
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

    @Provides
    fun provideFetchFavoritePicsUsecase(picRepository: PicRepository): FetchFavoritePicsUsecase =
        FetchFavoritePicsUsecaseImpl(picRepository)
}