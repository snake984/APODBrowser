package com.pandora.apodbrowser.picturedetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pandora.domain.usecases.IsPictureFavoriteUsecase
import com.pandora.domain.usecases.RemovePicFromFavoriteUsecase
import com.pandora.domain.usecases.SaveFavoritePicUsecase
import kotlin.coroutines.CoroutineContext

class PictureDetailViewModelFactory(
    private val isPictureFavoriteUsecase: IsPictureFavoriteUsecase,
    private val saveFavoritePicUsecase: SaveFavoritePicUsecase,
    private val removePicFromFavoriteUsecase: RemovePicFromFavoriteUsecase,
    private val backgroundCoroutineContext: CoroutineContext,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        PictureDetailViewModel(
            isPictureFavoriteUsecase,
            saveFavoritePicUsecase,
            removePicFromFavoriteUsecase,
            backgroundCoroutineContext
        ) as T
}