package com.example.weatherapplgctakehome.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplgctakehome.domain.WeatherRepository
import com.example.weatherapplgctakehome.domain.model.WeatherData
import com.example.weatherapplgctakehome.domain.usecases.GetCurrentWeatherByLocationUC
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _currentWeatherData = MutableStateFlow<WeatherData?>(null)
    val currentWeatherData = _currentWeatherData.asStateFlow()

    private val _currentWeatherDataList = MutableStateFlow<List<WeatherData?>>(emptyList())
    val currentWeatherDataList = _currentWeatherDataList.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun getCurrentWeatherByLocation(locationParam: String) {
        viewModelScope.launch {
            val getCurrentWeatherUC = GetCurrentWeatherByLocationUC(repository).invoke(locationParam)
            if (getCurrentWeatherUC is WeatherData) {
                _currentWeatherData.update { getCurrentWeatherUC }
                _currentWeatherDataList.update { it.plus(getCurrentWeatherUC) }
            } else {
                _error.update { getCurrentWeatherUC.toString() }
            }
        }
    }
}