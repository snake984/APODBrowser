package com.pandora.domain.usecases

import com.pandora.domain.model.PicOfTheDay
import com.pandora.domain.repositories.PicRepository

interface SaveFavoritePicUsecase {
    suspend fun saveFavoritePic(pic: PicOfTheDay)
}

internal class SaveFavoritePicUsecaseImpl(private val picRepository: PicRepository) :
    SaveFavoritePicUsecase {
    override suspend fun saveFavoritePic(pic: PicOfTheDay) = picRepository.saveFavoritePic(pic)
}