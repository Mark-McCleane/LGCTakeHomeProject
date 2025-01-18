package com.example.weatherapplgctakehome.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplgctakehome.domain.WeatherRepository
import com.example.weatherapplgctakehome.domain.model.AstronomyData
import com.example.weatherapplgctakehome.domain.model.WeatherData
import com.example.weatherapplgctakehome.domain.usecases.GetAstronomyByLocationUC
import com.example.weatherapplgctakehome.domain.usecases.GetCurrentWeatherByLocationUC
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _currentWeatherData = MutableStateFlow<WeatherData?>(null)
    val currentWeatherData = _currentWeatherData.asStateFlow()

    private val _currentAstronomyData = MutableStateFlow<AstronomyData?>(null)
    val currentAstronomyData = _currentAstronomyData.asStateFlow()

    private val _currentWeatherDataList = MutableStateFlow<List<WeatherData?>>(emptyList())
    val currentWeatherDataList = _currentWeatherDataList.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _title = MutableStateFlow<String?>(null)
    val title = _title.asStateFlow()

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun getCurrentWeatherByLocation(locationParam: String) {
        viewModelScope.launch {
            val getCurrentWeatherUC =
                GetCurrentWeatherByLocationUC(repository).invoke(locationParam)
            if (getCurrentWeatherUC is WeatherData) {
                _currentWeatherData.update { getCurrentWeatherUC }
                _currentWeatherDataList.update { it.plus(getCurrentWeatherUC) }
            } else {
                _error.update { getCurrentWeatherUC.toString() }
            }
        }
    }

    fun getAstronomyByLocation(
        locationParam: String,
        dateTime: String = LocalDate.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        )
    ) {
        viewModelScope.launch {
            val getAstronomyByLocationUC =
                GetAstronomyByLocationUC(repository).invoke(locationParam, dateTime)
            if (getAstronomyByLocationUC is AstronomyData) {
                _currentAstronomyData.update { getAstronomyByLocationUC }
            } else {
                _error.update { getAstronomyByLocationUC.toString() }
            }
        }
    }

    fun updateTitle(title: String) {
        _title.update { title }
    }
}