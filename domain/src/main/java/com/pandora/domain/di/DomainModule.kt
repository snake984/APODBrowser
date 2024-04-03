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
import com.pandora.domain.usecases.IsPictureFavoriteUsecase
import com.pandora.domain.usecases.IsPictureFavoriteUsecaseImpl
import com.pandora.domain.usecases.PagingKey
import com.pandora.domain.usecases.RemovePicFromFavoriteUsecase
import com.pandora.domain.usecases.RemovePicFromFavoriteUsecaseImpl
import com.pandora.domain.usecases.SaveFavoritePicUsecase
import com.pandora.domain.usecases.SaveFavoritePicUsecaseImpl
import dagger.Module
import dagger.Provides

@Module
object DomainModule {

    @Provides
    fun provideFetchPicsUsecase(picRepository: PicRepository): FetchPicsUsecase =
        FetchPicsUsecaseImpl(picRepository)

    @Provides
    fun provideFetchPaginatedPicsUsecase(pagingSource: PagingSource<PagingKey, PicOfTheDay>): FetchPaginatedPicsUsecase =
        FetchPaginatedPicsUsecaseImpl(pagingSource)

    @Provides
    fun provideFetchFavoritePicsUsecase(picRepository: PicRepository): FetchFavoritePicsUsecase =
        FetchFavoritePicsUsecaseImpl(picRepository)

    @Provides
    fun provideSaveFavoritePicUsecase(picRepository: PicRepository): SaveFavoritePicUsecase =
        SaveFavoritePicUsecaseImpl(picRepository)

    @Provides
    fun provideRemovePicFromFavoriteUsecase(picRepository: PicRepository): RemovePicFromFavoriteUsecase =
        RemovePicFromFavoriteUsecaseImpl(picRepository)

    @Provides
    fun provideIsPictureFavoriteUsecase(picRepository: PicRepository): IsPictureFavoriteUsecase =
        IsPictureFavoriteUsecaseImpl(picRepository)
}