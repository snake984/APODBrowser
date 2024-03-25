package com.pandora.storage.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_pics")
data class FavoritePicEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val date: String,
    val url: String,
    val hdUrl: String? = null,
    val filename: String? = null,
    val explanation: String? = null,
    val copyright: String? = null,
)