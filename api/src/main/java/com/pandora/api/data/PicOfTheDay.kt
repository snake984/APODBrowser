package com.pandora.api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PicOfTheDay(
    val title: String? = null,
    val date: String? = null,
    val explanation: String? = null,
    @SerialName("media_type") val mediaType: String? = null,
    val url: String? = null,
    @SerialName("hdurl") val hdUrl: String? = null,
    val copyright: String? = null,
    @SerialName("service_version") val serviceVersion: String? = null,
)