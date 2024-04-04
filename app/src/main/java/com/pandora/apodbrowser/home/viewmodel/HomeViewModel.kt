package com.pandora.apodbrowser.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import com.pandora.apodbrowser.ui.model.toItem
import com.pandora.domain.errors.mapToError
import com.pandora.domain.usecases.FetchPaginatedPicsUsecase
import com.pandora.domain.usecases.FetchPicsUsecase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import kotlin.coroutines.CoroutineContext

class HomeViewModel(
    private val fetchPicsUsecase: FetchPicsUsecase,
    fetchPaginatedPicsUsecase: FetchPaginatedPicsUsecase,
    private val backgroundCoroutineContext: CoroutineContext
) : ViewModel() {

    val pagedRandomPics = fetchPaginatedPicsUsecase.getPager()
        .flow
        .map { it.map { it.toItem() } }
        .cachedIn(viewModelScope)
        .flowOn(backgroundCoroutineContext)

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
        }.catch {
         _error.value = it.mapToError()
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _error = MutableStateFlow<Throwable?>(null)
    val error: StateFlow<Throwable?> = _error

    fun fetchLatestPicsOfTheDay() {
        viewModelScope.launch(backgroundCoroutineContext) {
            fetchPicsUsecase.getPicsOfTheDay(
                startDate = ZonedDateTime.now().minusDays(7),
                endDate = ZonedDateTime.now()
            ).map {
                it.map { it.toItem() }
            }.catch {
                _error.value = it.mapToError()
            }.collectLatest {
                _latestPicsOfTheDay.value = it
            }
        }
    }

    fun updateSearchResults(text: String) {
        _searchText.value = text
    }
}