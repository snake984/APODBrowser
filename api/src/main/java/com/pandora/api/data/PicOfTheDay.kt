package com.pandora.api.data

import com.google.gson.annotations.SerializedName


data class PicOfTheDay(
    val title: String? = null,
    val date: String? = null,
    val explanation: String? = null,
    @SerializedName("media_type") val mediaType: String? = null,
    val url: String? = null,
    @SerializedName("hdurl") val hdUrl: String? = null,
    val copyright: String? = null,
    @SerializedName("service_version") val serviceVersion: String? = null,
)