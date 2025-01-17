package com.example.weatherapplgctakehome.domain

import com.example.weatherapplgctakehome.data.entities.weatherByLocation.GetWeatherByLocationApiResponse
import com.example.weatherapplgctakehome.domain.model.WeatherData

interface WeatherDataMapper {
    fun map(original: GetWeatherByLocationApiResponse) : WeatherData
}