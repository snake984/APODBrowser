package com.pandora.apodbrowser.home.di

import com.pandora.apodbrowser.home.view.HomeFragment
import dagger.Subcomponent

@Subcomponent(modules = [HomeModule::class])
interface HomeComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(module: HomeModule): HomeComponent
    }
    fun inject(homeFragment: HomeFragment)
}

interface HomeComponentFactoryProvider {
    fun provideHomeComponentFactory(): HomeComponent.Factory
}