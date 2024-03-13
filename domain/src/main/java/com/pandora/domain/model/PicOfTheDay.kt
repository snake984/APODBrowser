package com.pandora.domain.model

data class PicOfTheDay(
    val title: String,
    val date: String,
    val url: String,
    val explanation: String? = null,
    val hdUrl: String? = null,
)