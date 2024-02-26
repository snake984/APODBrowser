package com.pandora.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.ZonedDateTime
import javax.inject.Inject

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
        private val jsonParser = Json { ignoreUnknownKeys = true }
        private val mediaType = MediaType.parse("application/json") ?: throw IllegalArgumentException("Wrong media type")
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            //TODO - Add CallAdapterFactory
            .addConverterFactory(jsonParser.asConverterFactory(mediaType))
            .build()
    }
}