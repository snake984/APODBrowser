package com.pandora.apodbrowser

import android.app.Application
import com.pandora.apodbrowser.di.AppComponent
import com.pandora.apodbrowser.di.DaggerAppComponent
import com.pandora.apodbrowser.home.di.HomeComponent
import com.pandora.apodbrowser.home.di.HomeComponentFactoryProvider

class APODBrowserApplication: Application(), HomeComponentFactoryProvider {

    private val appComponent: AppComponent = DaggerAppComponent.create()

    override fun provideHomeComponentFactory(): HomeComponent.Factory =
        appComponent.homeComponentFactory()
}