package com.pandora.apodbrowser.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pandora.domain.usecases.FetchPaginatedPicsUsecase
import com.pandora.domain.usecases.FetchPicsUsecase

class HomeViewModelFactory(
    private val fetchPicsUsecase: FetchPicsUsecase,
    private val fetchPaginatedPicsUsecase: FetchPaginatedPicsUsecase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        HomeViewModel(fetchPicsUsecase, fetchPaginatedPicsUsecase) as T
}