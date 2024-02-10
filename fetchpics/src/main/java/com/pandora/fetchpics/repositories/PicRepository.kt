package com.pandora.fetchpics.repositories

import com.pandora.api.RetrofitRequester
import com.pandora.fetchpics.model.PicOfTheDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.ZonedDateTime

interface PicRepository {
    fun getPicsOfTheDay(
        startDate: ZonedDateTime = ZonedDateTime.now(),
        endDate: ZonedDateTime? = null,
        randomCount: Int? = null,
    ): Flow<List<PicOfTheDay>>
}

class PicRepositoryImpl : PicRepository {

    private val requester: RetrofitRequester = RetrofitRequester()
    override fun getPicsOfTheDay(
        startDate: ZonedDateTime,
        endDate: ZonedDateTime?,
        randomCount: Int?,
    ): Flow<List<PicOfTheDay>> {
        val fetchedPics = if (randomCount == null) {
            requester.apodApi.fetchPicsOfTheDay(
                "XAQPfUbcpTfkhPvoz6cJ8xGGLVaa8Qg2dF30LpIe",
                startDate.toLocalDate(),
                endDate?.toLocalDate()
            ).execute()
        } else {
            requester.apodApi.fetchRandomPics(
                "XAQPfUbcpTfkhPvoz6cJ8xGGLVaa8Qg2dF30LpIe",
                randomCount
            ).execute()
        }.body() ?: emptyList()

        return flowOf(
            fetchedPics
                .filter { it.title != null && it.date != null && it.url != null }
                .map {
                    PicOfTheDay(
                        title = it.title ?: throw IllegalStateException("PicOfTheDay parsing error: title not found"),
                        date = it.date ?: throw IllegalStateException("PicOfTheDay parsing error: date not found"),
                        url = it.url ?: throw IllegalStateException("PicOfTheDay parsing error: url not found"),
                        explanation = it.explanation,
                        hdUrl = it.hdUrl,
                    )
                })
    }
}