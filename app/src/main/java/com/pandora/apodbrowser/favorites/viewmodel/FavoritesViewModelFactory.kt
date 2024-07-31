package com.pandora.apodbrowser.favorites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pandora.apodbrowser.di.AppModule
import com.pandora.domain.usecases.FetchFavoritePicsUsecase
import com.pandora.domain.usecases.IsPictureFavoriteUsecase
import com.pandora.domain.usecases.RemovePicFromFavoriteUsecase
import com.pandora.domain.usecases.SaveFavoritePicUsecase
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class FavoritesViewModelFactory(
    private val saveFavoritePicUsecase: SaveFavoritePicUsecase,
    private val removePicFromFavoriteUsecase: RemovePicFromFavoriteUsecase,
    private val isPictureFavoriteUsecase: IsPictureFavoriteUsecase,
    private val fetchFavoritePicsUsecase: FetchFavoritePicsUsecase,
    private val backgroundCoroutineContext: CoroutineContext,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        FavoritesViewModel(
            saveFavoritePicUsecase,
            removePicFromFavoriteUsecase,
            isPictureFavoriteUsecase,
            fetchFavoritePicsUsecase,
            backgroundCoroutineContext
        ) as T
}