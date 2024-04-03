package com.pandora.api

import com.pandora.api.converters.ByteArrayConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface Requester {
    val apodApi: APODApi
    val fileDownloader: FileDownloader

    companion object {
        const val APOD_API_KEY_DI_TAG = "apodApiKey"
    }
}

internal class RetrofitRequester : Requester {
    override val apodApi: APODApi =
        apodRetrofit.create(APODApi::class.java)

    override val fileDownloader: FileDownloader =
        Retrofit
            .Builder()
            //baseUrl is overridden in FileDownloader
            .baseUrl("https://api.nasa.gov/")
            .addConverterFactory(ByteArrayConverterFactory.create())
            .build()
            .create(FileDownloader::class.java)

    companion object {
        private val apodRetrofit = Retrofit
            .Builder()
            .baseUrl("https://api.nasa.gov/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}