package com.pandora.api.di

import com.pandora.api.BuildConfig
import com.pandora.api.Requester
import com.pandora.api.RetrofitRequester
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object ApiModule {
    @Provides
    fun provideRequester(): Requester = RetrofitRequester()

    @Provides
    @Named(Requester.APOD_API_KEY_DI_TAG)
    fun provideApodApiKey(): String = BuildConfig.apodApiKey
}