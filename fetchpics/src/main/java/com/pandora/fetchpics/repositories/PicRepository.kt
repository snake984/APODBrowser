package com.pandora.fetchpics.repositories

import com.pandora.fetchpics.model.PicOfTheDay
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime

interface PicRepository {
    fun getPicsOfTheDay(
        startDate: ZonedDateTime = ZonedDateTime.now(),
        endDate: ZonedDateTime? = null,
        randomCount: Int? = null,
    ): Flow<List<PicOfTheDay>>
}