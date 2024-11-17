package com.pandora.repositories

import com.pandora.api.Requester
import com.pandora.domain.errors.MappingError
import com.pandora.domain.model.FavoritePic
import com.pandora.domain.model.PicOfTheDay
import com.pandora.domain.repositories.PicRepository
import com.pandora.storage.database.dao.FavoritePicsDao
import com.pandora.storage.database.entities.FavoritePicEntity
import com.pandora.storage.filesystem.FileManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime

internal class PicRepositoryImpl(
    private val favoritePicsDao: FavoritePicsDao,
    private val fileManager: FileManager,
    private val requester: Requester,
    private val apiKey: String
) : PicRepository {

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
                    copyright = it.copyright,
                )
            })
    }

    override fun getFavorites(): Flow<List<FavoritePic>> =
        favoritePicsDao.getAll().map {
            it.map { dbPic ->
                FavoritePic(
                    title = dbPic.title,
                    date = dbPic.date,
                    url = dbPic.url,
                    hdUrl = dbPic.hdUrl,
                    explanation = dbPic.explanation,
                    imageUri = dbPic.uri,
                    copyright = dbPic.copyright,
                )
            }
        }

    override suspend fun saveFavoritePic(favorite: PicOfTheDay) {
        val url = favorite.hdUrl ?: favorite.url
        val filename = url.split("/").last()
        val downloadedImage = requester.fileDownloader.downloadFile(url)
        val uri = fileManager.saveImage(downloadedImage, filename)
        favoritePicsDao.insertAll(
            listOf(
                FavoritePicEntity(
                    title = favorite.title,
                    date = favorite.date,
                    url = favorite.url,
                    hdUrl = favorite.hdUrl,
                    explanation = favorite.explanation,
                    copyright = favorite.copyright,
                    uri = uri
                )
            )
        )
    }

    override suspend fun removePicFromFavorite(favorite: PicOfTheDay) {
        val entity = favoritePicsDao.getByDate(favorite.date)
        val uri = entity?.uri
        if (uri != null) {
            fileManager.deleteImage(uri)
        }
        favoritePicsDao.deleteByDate(favorite.date)
    }
}