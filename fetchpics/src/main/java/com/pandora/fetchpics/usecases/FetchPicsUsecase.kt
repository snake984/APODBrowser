package com.pandora.fetchpics.usecases

import com.pandora.fetchpics.model.PicOfTheDay
import com.pandora.fetchpics.repositories.PicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime
import javax.inject.Inject

interface FetchPicsUsecase {
    fun getPicsOfTheDay(
        startDate: ZonedDateTime = ZonedDateTime.now(),
        endDate: ZonedDateTime? = null,
        randomCount: Int? = null,
    ): Flow<List<PicOfTheDay>>
}

internal class FetchPicsUsecaseImpl @Inject constructor(private val picRepository: PicRepository) : FetchPicsUsecase {
    override fun getPicsOfTheDay(
        startDate: ZonedDateTime,
        endDate: ZonedDateTime?,
        randomCount: Int?
    ): Flow<List<PicOfTheDay>> {
        return picRepository.getPicsOfTheDay(startDate, endDate, randomCount)
    }
}
