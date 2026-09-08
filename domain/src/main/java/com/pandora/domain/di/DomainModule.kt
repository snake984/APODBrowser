package com.pandora.domain.di

import com.pandora.domain.usecases.FetchFavoritePicsUsecase
import com.pandora.domain.usecases.FetchFavoritePicsUsecaseImpl
import com.pandora.domain.usecases.FetchPaginatedPicsUsecase
import com.pandora.domain.usecases.FetchPaginatedPicsUsecaseImpl
import com.pandora.domain.usecases.FetchPicsUsecase
import com.pandora.domain.usecases.FetchPicsUsecaseImpl
import com.pandora.domain.usecases.IsPictureFavoriteUsecase
import com.pandora.domain.usecases.IsPictureFavoriteUsecaseImpl
import com.pandora.domain.usecases.RemovePicFromFavoriteUsecase
import com.pandora.domain.usecases.RemovePicFromFavoriteUsecaseImpl
import com.pandora.domain.usecases.SaveFavoritePicUsecase
import com.pandora.domain.usecases.SaveFavoritePicUsecaseImpl
import org.koin.dsl.module

val domainModule = module {
    factory<FetchPicsUsecase> { FetchPicsUsecaseImpl(get()) }
    factory<FetchPaginatedPicsUsecase> { FetchPaginatedPicsUsecaseImpl(get()) }
    factory<FetchFavoritePicsUsecase> { FetchFavoritePicsUsecaseImpl(get()) }
    factory<SaveFavoritePicUsecase> { SaveFavoritePicUsecaseImpl(get()) }
    factory<RemovePicFromFavoriteUsecase> { RemovePicFromFavoriteUsecaseImpl(get()) }
    factory<IsPictureFavoriteUsecase> { IsPictureFavoriteUsecaseImpl(get()) }
}