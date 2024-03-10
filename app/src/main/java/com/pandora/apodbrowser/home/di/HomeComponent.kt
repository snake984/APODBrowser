package com.pandora.apodbrowser.home.di

import com.pandora.apodbrowser.MainActivity
import com.pandora.apodbrowser.home.viewmodel.HomeViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [HomeModule::class])
interface HomeComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(module: HomeModule): HomeComponent
    }
    fun homeViewModelFactory(): HomeViewModelFactory
}

interface HomeComponentFactoryProvider {
    fun provideHomeComponentFactory(): HomeComponent.Factory
}