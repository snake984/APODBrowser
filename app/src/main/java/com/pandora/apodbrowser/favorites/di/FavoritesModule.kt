package com.pandora.apodbrowser.favorites.di

import com.pandora.apodbrowser.di.AppModule
import com.pandora.apodbrowser.di.ScreenScope
import com.pandora.apodbrowser.favorites.viewmodel.FavoritesViewModelFactory
import com.pandora.domain.usecases.FetchFavoritePicsUsecase
import com.pandora.domain.usecases.IsPictureFavoriteUsecase
import com.pandora.domain.usecases.RemovePicFromFavoriteUsecase
import com.pandora.domain.usecases.SaveFavoritePicUsecase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

@Module
object FavoritesModule {

    @ScreenScope
    @Provides
    fun provideFavoritesViewModelFactory(
        saveFavoritePicUsecase: SaveFavoritePicUsecase,
        removePicFromFavoriteUsecase: RemovePicFromFavoriteUsecase,
        isPictureFavoriteUsecase: IsPictureFavoriteUsecase,
        fetchFavoritePicsUsecase: FetchFavoritePicsUsecase,
        @Named(AppModule.IO_BACKGROUND_COROUTINE_CONTEXT_KEY)
        backgroundCoroutineContext: CoroutineContext,
    ): FavoritesViewModelFactory =
        FavoritesViewModelFactory(
            saveFavoritePicUsecase,
            removePicFromFavoriteUsecase,
            isPictureFavoriteUsecase,
            fetchFavoritePicsUsecase,
            backgroundCoroutineContext
        )
}