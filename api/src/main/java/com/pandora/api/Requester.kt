package com.pandora.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface Requester {
    val apodApi: APODApi

    companion object {
        const val APOD_API_KEY_DI_TAG = "apodApiKey"
    }
}

internal class RetrofitRequester : Requester {
    override val apodApi: APODApi =
        retrofit.create(APODApi::class.java)

    companion object {
        private val retrofit = Retrofit
            .Builder()
            .baseUrl("https://api.nasa.gov/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}