package com.pandora.api.di

import com.pandora.api.Requester
import com.pandora.api.RetrofitRequester
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApiModule {
    @Provides
    fun provideRequester(): Requester = RetrofitRequester()
}