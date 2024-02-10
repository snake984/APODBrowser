package com.pandora.api

import com.pandora.api.data.PicOfTheDay
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate

interface APODApi {
    @GET("planetary/apod")
    fun fetchPicsOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("start_date") startDate: LocalDate,
        @Query("end_date") endDate: LocalDate? = null
    ): Call<List<PicOfTheDay>>

    @GET("planetary/apod")
    fun fetchRandomPics(@Query("api_key") apiKey: String, @Query("count") count: Int): Call<List<PicOfTheDay>>
}