package com.pandora.apodbrowser.favorites.di

import com.pandora.apodbrowser.di.ScreenScope
import com.pandora.apodbrowser.favorites.viewmodel.FavoritesViewModelFactory
import dagger.Subcomponent

@ScreenScope
@Subcomponent(modules = [FavoritesModule::class])
interface FavoritesComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(module: FavoritesModule): FavoritesComponent
    }
    fun favoritesViewModelFactory(): FavoritesViewModelFactory
}

interface FavoritesComponentFactoryProvider {
    fun provideFavoritesComponentFactory(): FavoritesComponent.Factory
}