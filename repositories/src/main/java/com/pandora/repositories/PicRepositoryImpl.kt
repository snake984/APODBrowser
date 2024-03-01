package com.pandora.repositories

import com.pandora.api.Requester
import com.pandora.fetchpics.model.PicOfTheDay
import com.pandora.fetchpics.repositories.PicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.ZonedDateTime

internal class PicRepositoryImpl(private val requester: Requester, private val apiKey: String) :
    PicRepository {

    override fun getPicsOfTheDay(
        startDate: ZonedDateTime,
        endDate: ZonedDateTime?,
        randomCount: Int?,
    ): Flow<List<PicOfTheDay>> = flow {
        val fetchedPics = if (randomCount == null) {
            requester.apodApi.fetchPicsOfTheDay(
                apiKey,
                startDate.toLocalDate(),
                endDate?.toLocalDate()
            )
        } else {
            requester.apodApi.fetchRandomPics(
                apiKey,
                randomCount
            )
        }

        emit(fetchedPics
            .filter { it.title != null && it.date != null && it.url != null && it.mediaType == "image" }
            .map {
            PicOfTheDay(
                title = it.title
                    ?: throw IllegalStateException("PicOfTheDay parsing error: title not found"),
                date = it.date
                    ?: throw IllegalStateException("PicOfTheDay parsing error: date not found"),
                url = it.url
                    ?: throw IllegalStateException("PicOfTheDay parsing error: url not found"),
                explanation = it.explanation,
                hdUrl = it.hdUrl,
            )
        })
    }
}