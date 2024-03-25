package com.pandora.apodbrowser.di

import com.pandora.api.di.ApiModule
import com.pandora.apodbrowser.home.di.HomeComponent
import com.pandora.domain.di.FetchPicsModule
import com.pandora.repositories.di.RepositoriesModule
import com.pandora.storage.di.StorageModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, ApiModule::class, StorageModule::class, RepositoriesModule::class, FetchPicsModule::class])
@Singleton
interface AppComponent {
    fun homeComponentFactory(): HomeComponent.Factory
}