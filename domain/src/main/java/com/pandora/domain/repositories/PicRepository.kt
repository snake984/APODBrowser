package com.pandora.domain.repositories

import com.pandora.domain.model.FavoritePic
import com.pandora.domain.model.PicOfTheDay
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime

interface PicRepository {
    fun getPicsOfTheDay(
        startDate: ZonedDateTime = ZonedDateTime.now(),
        endDate: ZonedDateTime? = null,
        randomCount: Int? = null,
    ): Flow<List<PicOfTheDay>>

    fun getFavorites(): Flow<List<FavoritePic>>

    suspend fun saveFavoritePic(favorite: PicOfTheDay)

    suspend fun removePicFromFavorite(favorite: PicOfTheDay)
}