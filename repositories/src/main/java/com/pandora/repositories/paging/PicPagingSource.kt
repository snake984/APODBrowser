package com.pandora.repositories.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pandora.fetchpics.model.PicOfTheDay
import com.pandora.fetchpics.repositories.PicRepository
import com.pandora.fetchpics.usecases.FetchPaginatedPicsUsecase
import com.pandora.fetchpics.usecases.PagingKey
import kotlinx.coroutines.flow.first

class PicPagingSource(private val picRepository: PicRepository) :
    PagingSource<PagingKey, PicOfTheDay>() {
    override fun getRefreshKey(state: PagingState<PagingKey, PicOfTheDay>): PagingKey? =
        state.anchorPosition?.let {
            val daysOffset = FetchPaginatedPicsUsecase.PAGE_SIZE + 1L
            val result = state.closestPageToPosition(it)?.prevKey?.startDate?.plusDays(daysOffset)
                ?: state.closestPageToPosition(it)?.nextKey?.startDate?.minusDays(daysOffset)

            result?.let { date -> PagingKey(date) }
        }

    override suspend fun load(params: LoadParams<PagingKey>): LoadResult<PagingKey, PicOfTheDay> =
        try {
            computePage(params)
        } catch (error: Throwable) {
            LoadResult.Error(error)
        }

    private suspend fun computePage(params: LoadParams<PagingKey>): LoadResult.Page<PagingKey, PicOfTheDay> {
        val pageIndex = params.key ?: FetchPaginatedPicsUsecase.INITIAL_PAGING_KEY
        val daysOffset = FetchPaginatedPicsUsecase.PAGE_SIZE + 1L
        val previousKey = params.key?.copy(startDate = pageIndex.startDate.plusDays(daysOffset))
        val nextKey = pageIndex.copy(startDate = pageIndex.startDate.minusDays(daysOffset))
        val pics = picRepository.getPicsOfTheDay(
            pageIndex.startDate.minusDays(FetchPaginatedPicsUsecase.PAGE_SIZE.toLong()),
            pageIndex.startDate
        ).first()

        return LoadResult.Page(
            data = pics,
            prevKey = previousKey,
            nextKey = nextKey
        )
    }
}