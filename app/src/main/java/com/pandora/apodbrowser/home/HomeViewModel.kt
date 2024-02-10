package com.pandora.apodbrowser.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandora.fetchpics.model.PicOfTheDay
import com.pandora.fetchpics.repositories.PicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class HomeViewModel(private val fetchRepository: PicRepository): ViewModel() {
    private val _latestPicsOfTheDay = MutableStateFlow<List<PicOfTheDay>>(emptyList())
    val latestPicsOfTheDay: StateFlow<List<PicOfTheDay>> = _latestPicsOfTheDay

    private val _randomPicsOfTheDay = MutableStateFlow<List<PicOfTheDay>>(emptyList())
    val randomPicsOfTheDay: StateFlow<List<PicOfTheDay>> = _randomPicsOfTheDay

    fun fetchLatestPicsOfTheDay() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchRepository.getPicsOfTheDay(startDate = ZonedDateTime.now().minusDays(7), endDate = ZonedDateTime.now())
                .collectLatest {
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
}