package com.pandora.apodbrowser.favorites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import com.pandora.domain.usecases.FetchFavoritePicsUsecase
import com.pandora.domain.usecases.IsPictureFavoriteUsecase
import com.pandora.domain.usecases.RemovePicFromFavoriteUsecase
import com.pandora.domain.usecases.SaveFavoritePicUsecase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FavoritesViewModel(
    private val saveFavoritePicUsecase: SaveFavoritePicUsecase,
    private val removePicFromFavoriteUsecase: RemovePicFromFavoriteUsecase,
    private val isPictureFavoriteUsecase: IsPictureFavoriteUsecase,
    private val fetchFavoritePicsUsecase: FetchFavoritePicsUsecase,
    private val backgroundCoroutineContext: CoroutineContext,
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<PicOfTheDayItem>>(emptyList())
    val favorites: StateFlow<List<PicOfTheDayItem>> = _favorites

    fun fetchFavorites() {
        viewModelScope.launch(backgroundCoroutineContext) {
            fetchFavoritePicsUsecase.fetchFavoritePics().collectLatest {
                _favorites.value = it.
            }
        }
    }
}