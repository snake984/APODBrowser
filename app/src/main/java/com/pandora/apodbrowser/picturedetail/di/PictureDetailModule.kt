package com.pandora.apodbrowser.picturedetail.di

import com.pandora.apodbrowser.di.AppModule
import com.pandora.apodbrowser.di.ScreenScope
import com.pandora.apodbrowser.picturedetail.viewmodel.PictureDetailViewModelFactory
import com.pandora.domain.usecases.IsPictureFavoriteUsecase
import com.pandora.domain.usecases.RemovePicFromFavoriteUsecase
import com.pandora.domain.usecases.SaveFavoritePicUsecase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

@Module
object PictureDetailModule {

    @Provides
    @ScreenScope
    fun providePictureDetailViewModelFactory(
        isPictureFavoriteUsecase: IsPictureFavoriteUsecase,
        saveFavoritePicUsecase: SaveFavoritePicUsecase,
        removePicFromFavoriteUsecase: RemovePicFromFavoriteUsecase,
        @Named(AppModule.IO_BACKGROUND_COROUTINE_CONTEXT_KEY) backgroundCoroutineContext: CoroutineContext,
    ): PictureDetailViewModelFactory =
        PictureDetailViewModelFactory(
            isPictureFavoriteUsecase,
            saveFavoritePicUsecase,
            removePicFromFavoriteUsecase,
            backgroundCoroutineContext
        )
}