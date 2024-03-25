package com.pandora.apodbrowser.di

import android.content.Context
import com.pandora.apodbrowser.home.di.HomeComponent
import dagger.Module
import dagger.Provides

@Module(subcomponents = [HomeComponent::class])
class AppModule(private val applicationContext: Context) {

    @Provides
    fun provideApplicationContext(): Context = applicationContext
}