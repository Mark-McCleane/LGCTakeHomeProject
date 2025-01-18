package com.example.weatherapplgctakehome.domain

import com.example.weatherapplgctakehome.data.entities.astronomyByLocation.GetAstronomyByLocationApiResult
import com.example.weatherapplgctakehome.data.entities.weatherByLocation.GetWeatherByLocationApiResponse
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface WeatherRepository {
    suspend fun getCurrentWeatherByLocation(
        apiKey: String,
        location: String
    ): Response<GetWeatherByLocationApiResponse>

    suspend fun getAstronomyByLocation(
        apiKey: String,
        location: String,
        dateTime: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    ): Response<GetAstronomyByLocationApiResult>
}