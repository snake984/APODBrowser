package com.pandora.api.di

import com.pandora.api.BuildConfig
import com.pandora.api.Requester
import com.pandora.api.RetrofitRequester
import org.koin.core.qualifier.named
import org.koin.dsl.module

val apiModule = module {
    single<Requester> { RetrofitRequester() }
    single(named(Requester.APOD_API_KEY_DI_TAG)) { BuildConfig.apodApiKey }
}