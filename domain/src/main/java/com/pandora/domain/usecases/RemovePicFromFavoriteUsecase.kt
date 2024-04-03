package com.pandora.domain.usecases

import com.pandora.domain.model.PicOfTheDay
import com.pandora.domain.repositories.PicRepository

interface RemovePicFromFavoriteUsecase {

    suspend fun removePicFromFavorite(pic: PicOfTheDay)
}

internal class RemovePicFromFavoriteUsecaseImpl(private val picRepository: PicRepository) :
    RemovePicFromFavoriteUsecase {
    override suspend fun removePicFromFavorite(pic: PicOfTheDay) {
        picRepository.removePicFromFavorite(pic)
    }
}