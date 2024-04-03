package com.pandora.domain.usecases

import com.pandora.domain.model.FavoritePic
import com.pandora.domain.repositories.PicRepository
import kotlinx.coroutines.flow.Flow

interface FetchFavoritePicsUsecase {
    fun fetchFavoritePics(): Flow<List<FavoritePic>>
}

internal class FetchFavoritePicsUsecaseImpl(private val picRepository: PicRepository) :
    FetchFavoritePicsUsecase {
    override fun fetchFavoritePics(): Flow<List<FavoritePic>> =
        picRepository.getFavorites()
}