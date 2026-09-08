package com.pandora.repositories.di

import androidx.paging.PagingSource
import com.pandora.api.Requester
import com.pandora.domain.model.PicOfTheDay
import com.pandora.domain.repositories.PicRepository
import com.pandora.domain.usecases.PagingKey
import com.pandora.repositories.PicRepositoryImpl
import com.pandora.repositories.paging.PicPagingSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoriesModule = module {
    single<PicRepository> {
        PicRepositoryImpl(
            favoritePicsDao = get(),
            fileManager = get(),
            requester = get(),
            apiKey = get(named(Requester.APOD_API_KEY_DI_TAG))
        )
    }
    factory<PagingSource<PagingKey, PicOfTheDay>> { PicPagingSource(get()) }
}