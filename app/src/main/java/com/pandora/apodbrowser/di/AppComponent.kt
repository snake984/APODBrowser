package com.pandora.apodbrowser.di

import com.pandora.api.di.ApiModule
import com.pandora.apodbrowser.home.di.HomeComponent
import com.pandora.fetchpics.di.FetchPicsModule
import com.pandora.repositories.di.RepositoriesModule
import dagger.Component

@Component(modules = [AppModule::class, ApiModule::class, RepositoriesModule::class, FetchPicsModule::class])
interface AppComponent {
    fun homeComponentFactory(): HomeComponent.Factory
}