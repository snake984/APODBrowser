package com.pandora.repositories

import com.pandora.api.Requester
import com.pandora.domain.errors.MappingError
import com.pandora.domain.model.PicOfTheDay
import com.pandora.domain.repositories.PicRepository
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
                        ?: throw MappingError("PicOfTheDay parsing error: title not found"),
                    date = it.date
                        ?: throw MappingError("PicOfTheDay parsing error: date not found"),
                    url = it.url
                        ?: throw MappingError("PicOfTheDay parsing error: url not found"),
                    explanation = it.explanation,
                    hdUrl = it.hdUrl,
                )
            })
    }
}