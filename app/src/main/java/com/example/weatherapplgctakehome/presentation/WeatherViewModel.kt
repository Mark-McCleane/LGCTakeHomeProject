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

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState())
    val uiState = _uiState.asStateFlow()

    fun onSearchTextChange(text: String) {
        _uiState.update { it.copy(searchText = text) }
    }

    fun getCurrentWeatherByLocation(locationParam: String) {
        viewModelScope.launch {
            val getCurrentWeatherUC =
                GetCurrentWeatherByLocationUC(repository).invoke(locationParam)
            if (getCurrentWeatherUC is WeatherData) {
                _uiState.update {
                    it.copy(
                        currentWeatherData = getCurrentWeatherUC,
                        currentWeatherDataList = it.currentWeatherDataList.plus(getCurrentWeatherUC)
                    )
                }
            } else {
                _uiState.update { it.copy(error = getCurrentWeatherUC.toString()) }
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
                _uiState.update { it.copy(currentAstronomyData = getAstronomyByLocationUC) }
            } else {
                _uiState.update { it.copy(error = getAstronomyByLocationUC.toString()) }
            }
        }
    }

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun updateBackIconVisibility(value: Boolean) {
        _uiState.update { state -> state.copy(backIconVisible = value) }
    }
}

data class WeatherUiState(
    val searchText: String = "",
    val currentWeatherData: WeatherData? = null,
    val currentAstronomyData: AstronomyData? = null,
    val currentWeatherDataList: List<WeatherData?> = emptyList(),
    val error: String? = null,
    val title: String? = null,
    val backIconVisible: Boolean = false
)