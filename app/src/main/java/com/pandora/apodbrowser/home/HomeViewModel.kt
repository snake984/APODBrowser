package com.pandora.apodbrowser.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pandora.fetchpics.model.PicOfTheDay
import com.pandora.fetchpics.repositories.PicRepository
import com.pandora.fetchpics.repositories.PicRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class HomeViewModel(private val fetchRepository: PicRepository) : ViewModel() {
    private val _latestPicsOfTheDay = MutableStateFlow<List<PicOfTheDay>>(emptyList())
    val latestPicsOfTheDay: StateFlow<List<PicOfTheDay>> = _latestPicsOfTheDay

    private val _randomPicsOfTheDay = MutableStateFlow<List<PicOfTheDay>>(emptyList())
    val randomPicsOfTheDay: StateFlow<List<PicOfTheDay>> = _randomPicsOfTheDay

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    private val _searchResults = _latestPicsOfTheDay
    val searchResults: StateFlow<List<PicOfTheDay>> =
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
            fetchRepository.getPicsOfTheDay(
                startDate = ZonedDateTime.now().minusDays(7),
                endDate = ZonedDateTime.now()
            ).collectLatest {
                    _latestPicsOfTheDay.value = it
                }
        }
    }

    fun fetchRandomPicsOfTheDay() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchRepository.getPicsOfTheDay(randomCount = 5).collectLatest {
                _randomPicsOfTheDay.value = it
            }
        }
    }

    fun updateSearchResults(text: String) {
        _searchText.value = text
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                HomeViewModel(PicRepositoryImpl())
            }
        }
    }

}