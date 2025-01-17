package com.example.weatherapplgctakehome.domain.model

data class WeatherData(
    val locationName: String = "",
    val localTime: String = "",
    val currentTempInCelsius: Double = 0.0,
    val currentTempInFahrenheit: Double = 0.0,
    val weatherCondition: WeatherCondition?
)

data class WeatherCondition(val text: String? = "", val icon: String? = "", val code: Int? = 0)
