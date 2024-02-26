package com.pandora.apodbrowser.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pandora.fetchpics.usecases.FetchPicsUsecase
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(private val fetchPicsUsecase: FetchPicsUsecase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        HomeViewModel(fetchPicsUsecase) as T
}