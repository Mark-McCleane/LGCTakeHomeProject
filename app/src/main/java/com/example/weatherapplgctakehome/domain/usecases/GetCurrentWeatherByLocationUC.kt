package com.example.weatherapplgctakehome.domain.usecases

import com.example.weatherapplgctakehome.data.WeatherDataMapperImpl
import com.example.weatherapplgctakehome.domain.WeatherRepository

class GetCurrentWeatherByLocationUC(
    private val repository: WeatherRepository,
) {
    suspend operator fun invoke(locationParam: String): Any {
        val apiKey = "bcaf6500ac794e0eb16172753251701"  //System.getProperty("API_KEY") ?: ""
        val data = repository.getCurrentWeatherByLocation(apiKey, locationParam)
        if (data.isSuccessful && data.body() != null) {
            val mapper = WeatherDataMapperImpl()
            val weatherData = mapper.map(data.body()!!) // Safe to unwrap as checked above
            return weatherData
        } else {
            // Handle errors
            return data.message() ?: "Unknown Error"
        }
    }
}