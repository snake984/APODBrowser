package com.pandora.apodbrowser.di

import com.pandora.apodbrowser.favorites.viewmodel.FavoritesViewModel
import com.pandora.apodbrowser.home.viewmodel.HomeViewModel
import com.pandora.apodbrowser.picturedetail.viewmodel.PictureDetailViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

const val DEFAULT_BACKGROUND_COROUTINE_CONTEXT_KEY = "Default"
const val IO_BACKGROUND_COROUTINE_CONTEXT_KEY = "IO"

val appModule = module {
    single<CoroutineContext>(named(DEFAULT_BACKGROUND_COROUTINE_CONTEXT_KEY)) {
        Dispatchers.Default
    }
    single<CoroutineContext>(named(IO_BACKGROUND_COROUTINE_CONTEXT_KEY)) {
        Dispatchers.IO
    }

    viewModel {
        HomeViewModel(
            fetchPicsUsecase = get(),
            fetchPaginatedPicsUsecase = get(),
            backgroundCoroutineContext = get(named(DEFAULT_BACKGROUND_COROUTINE_CONTEXT_KEY))
        )
    }
    viewModel {
        FavoritesViewModel(
            saveFavoritePicUsecase = get(),
            removePicFromFavoriteUsecase = get(),
            isPictureFavoriteUsecase = get(),
            fetchFavoritePicsUsecase = get(),
            backgroundCoroutineContext = get(named(IO_BACKGROUND_COROUTINE_CONTEXT_KEY))
        )
    }
    viewModel {
        PictureDetailViewModel(
            isPictureFavoriteUsecase = get(),
            saveFavoritePicUsecase = get(),
            removePicFromFavoriteUsecase = get(),
            backgroundCoroutineContext = get(named(IO_BACKGROUND_COROUTINE_CONTEXT_KEY))
        )
    }
}
