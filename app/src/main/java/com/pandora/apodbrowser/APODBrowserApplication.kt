package com.pandora.apodbrowser

import android.app.Application
import com.pandora.apodbrowser.di.AppComponent
import com.pandora.apodbrowser.di.AppModule
import com.pandora.apodbrowser.di.DaggerAppComponent
import com.pandora.apodbrowser.home.di.HomeComponent
import com.pandora.apodbrowser.home.di.HomeComponentFactoryProvider
import com.pandora.apodbrowser.picturedetail.di.PictureDetailComponent
import com.pandora.apodbrowser.picturedetail.di.PictureDetailComponentFactoryProvider

class APODBrowserApplication : Application(), HomeComponentFactoryProvider,
    PictureDetailComponentFactoryProvider {

    private val appComponent: AppComponent = DaggerAppComponent
        .builder()
        .appModule(AppModule(this))
        .build()

    override fun provideHomeComponentFactory(): HomeComponent.Factory =
        appComponent.homeComponentFactory()

    override fun providePictureDetailComponentFactory(): PictureDetailComponent.Factory =
        appComponent.pictureDetailComponentFactory()
}