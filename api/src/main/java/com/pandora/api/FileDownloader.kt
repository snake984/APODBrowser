package com.pandora.api

import com.pandora.api.data.PicOfTheDay
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface FileDownloader {
    @GET
    suspend fun downloadFile(@Url url: String): ByteArray
}