package com.pandora.apodbrowser.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import com.pandora.apodbrowser.ui.model.toItem
import com.pandora.fetchpics.usecases.FetchPaginatedPicsUsecase
import com.pandora.fetchpics.usecases.FetchPicsUsecase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class HomeViewModel(
    private val fetchPicsUsecase: FetchPicsUsecase,
    fetchPaginatedPicsUsecase: FetchPaginatedPicsUsecase
) : ViewModel() {

    val pagedRandomPics = fetchPaginatedPicsUsecase.getPager()
        .flow
        .map { it.map { it.toItem() } }
        .cachedIn(viewModelScope)

    private val _latestPicsOfTheDay = MutableStateFlow<List<PicOfTheDayItem>>(emptyList())
    val latestPicsOfTheDay: StateFlow<List<PicOfTheDayItem>> = _latestPicsOfTheDay

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    private val _searchResults = _latestPicsOfTheDay
    val searchResults: StateFlow<List<PicOfTheDayItem>> =
        _searchResults.combine(_searchText.debounce(500)) { pics, text ->
            if (text.isBlank()) {
                pics
            } else {
                pics.filter {
                    it.title.lowercase().contains(text.lowercase()) ||
                            it.explanation?.lowercase()?.contains(text.lowercase()) == true
                }
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun fetchLatestPicsOfTheDay() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchPicsUsecase.getPicsOfTheDay(
                startDate = ZonedDateTime.now().minusDays(7),
                endDate = ZonedDateTime.now()
            ).map {
                it.map { it.toItem() }
            }.collectLatest {
                _latestPicsOfTheDay.value = it
            }
        }
    }

    fun updateSearchResults(text: String) {
        _searchText.value = text
    }
}