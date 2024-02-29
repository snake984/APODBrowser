package com.pandora.fetchpics.usecases

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.pandora.fetchpics.model.PicOfTheDay
import com.pandora.fetchpics.repositories.PicRepository
import kotlinx.coroutines.flow.Flow
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
    }
}

