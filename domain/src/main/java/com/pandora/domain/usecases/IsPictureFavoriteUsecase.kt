package com.pandora.domain.usecases

import com.pandora.domain.model.PicOfTheDay
import com.pandora.domain.repositories.PicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface IsPictureFavoriteUsecase {

    fun isPictureFavorite(pic: PicOfTheDay): Flow<Boolean>
}

internal class IsPictureFavoriteUsecaseImpl(private val repository: PicRepository): IsPictureFavoriteUsecase {
    override fun isPictureFavorite(pic: PicOfTheDay): Flow<Boolean> =
        repository.getFavorites().map { it.firstOrNull { pic.date == it.date } != null }

}