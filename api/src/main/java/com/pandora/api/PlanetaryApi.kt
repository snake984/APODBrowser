package com.pandora.api

import com.pandora.api.data.PicOfTheDay
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate

interface APODApi {
    @GET("planetary/apod")
    suspend fun fetchPicsOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("start_date") startDate: LocalDate,
        @Query("end_date") endDate: LocalDate? = null
    ): List<PicOfTheDay>

    @GET("planetary/apod")
    suspend fun fetchRandomPics(@Query("api_key") apiKey: String, @Query("count") count: Int): List<PicOfTheDay>
}