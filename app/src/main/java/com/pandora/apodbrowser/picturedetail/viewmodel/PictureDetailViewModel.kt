package com.pandora.apodbrowser.picturedetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import com.pandora.apodbrowser.ui.model.toDomainModel
import com.pandora.domain.usecases.IsPictureFavoriteUsecase
import com.pandora.domain.usecases.RemovePicFromFavoriteUsecase
import com.pandora.domain.usecases.SaveFavoritePicUsecase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PictureDetailViewModel(
    private val isPictureFavoriteUsecase: IsPictureFavoriteUsecase,
    private val saveFavoritePicUsecase: SaveFavoritePicUsecase,
    private val removePicFromFavoriteUsecase: RemovePicFromFavoriteUsecase,
    private val backgroundCoroutineContext: CoroutineContext,
) : ViewModel() {

    private val _favoriteState = MutableStateFlow(PictureFavoriteState.LOADING)
    val favoriteState: StateFlow<PictureFavoriteState> = _favoriteState

    fun saveFavorite(picture: PicOfTheDayItem) {
        viewModelScope.launch(backgroundCoroutineContext) {
            saveFavoritePicUsecase.saveFavoritePic(picture.toDomainModel())
            _favoriteState.value = PictureFavoriteState.FAVORITE_ADDED
        }
    }

    fun removeFavorite(picture: PicOfTheDayItem) {
        viewModelScope.launch(backgroundCoroutineContext) {
            removePicFromFavoriteUsecase.removePicFromFavorite(picture.toDomainModel())
            _favoriteState.value = PictureFavoriteState.FAVORITE_REMOVED
        }
    }

    fun isPictureFavorite(picture: PicOfTheDayItem) {
        viewModelScope.launch(backgroundCoroutineContext) {
            isPictureFavoriteUsecase.isPictureFavorite(picture.toDomainModel())
                .collectLatest { isFavorite ->
                    _favoriteState.value = if (isFavorite) {
                        PictureFavoriteState.IS_FAVORITE
                    } else {
                        PictureFavoriteState.IS_NOT_FAVORITE
                    }
                }
        }
    }

    enum class PictureFavoriteState {
        FAVORITE_ADDED,
        FAVORITE_REMOVED,
        IS_FAVORITE,
        IS_NOT_FAVORITE,
        LOADING
    }
}