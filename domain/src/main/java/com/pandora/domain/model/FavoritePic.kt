package com.pandora.domain.model

data class FavoritePic(
    val title: String,
    val date: String,
    val url: String,
    val explanation: String? = null,
    val hdUrl: String? = null,
    val imagePath: String? = null,
    val copyright: String? = null,
)