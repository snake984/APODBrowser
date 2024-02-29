package com.pandora.fetchpics.usecases

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.pandora.fetchpics.model.PicOfTheDay
import com.pandora.fetchpics.usecases.FetchPaginatedPicsUsecase.Companion.PAGE_SIZE
import java.time.ZonedDateTime
import kotlin.random.Random

interface FetchPaginatedPicsUsecase {
    fun getPager(): Pager<PagingKey, PicOfTheDay>

    companion object {
        const val PAGE_SIZE = 10
        val INITIAL_PAGING_KEY = PagingKey(ZonedDateTime.now().minusDays((50..10000).random().toLong()))
    }
}

internal class FetchPaginatedPicsUsecaseImpl(private val picPagingSource: PagingSource<PagingKey, PicOfTheDay>): FetchPaginatedPicsUsecase {
    @OptIn(ExperimentalPagingApi::class)
    override fun getPager(): Pager<PagingKey, PicOfTheDay> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = { picPagingSource },
        remoteMediator = null
    )
}

data class PagingKey(val startDate: ZonedDateTime)