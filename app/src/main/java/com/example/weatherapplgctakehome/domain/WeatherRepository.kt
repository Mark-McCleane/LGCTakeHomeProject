package com.example.weatherapplgctakehome.domain

import com.example.weatherapplgctakehome.data.entities.weatherByLocation.GetWeatherByLocationApiResponse
import retrofit2.Response

interface WeatherRepository {
    suspend fun getCurrentWeatherByLocation(
        apiKey: String,
        location: String
    ): Response<GetWeatherByLocationApiResponse>
}