package com.pandora.domain.usecases

import com.pandora.domain.model.PicOfTheDay
import com.pandora.domain.repositories.PicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime

interface FetchPicsUsecase {
    fun getPicsOfTheDay(
        startDate: ZonedDateTime = ZonedDateTime.now(),
        endDate: ZonedDateTime? = null,
        randomCount: Int? = null,
    ): Flow<List<PicOfTheDay>>
}

internal class FetchPicsUsecaseImpl(
    private val picRepository: PicRepository
) : FetchPicsUsecase {
    override fun getPicsOfTheDay(
        startDate: ZonedDateTime,
        endDate: ZonedDateTime?,
        randomCount: Int?
    ): Flow<List<PicOfTheDay>> {
        return picRepository.getPicsOfTheDay(startDate, endDate, randomCount)
            .map { it.reversed() }
    }
}

