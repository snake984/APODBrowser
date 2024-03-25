package com.pandora.domain.usecases

import com.pandora.domain.model.PicOfTheDay

interface SaveFavoritePicUsecase {
    fun saveFavoritePic(pic: PicOfTheDay)
}