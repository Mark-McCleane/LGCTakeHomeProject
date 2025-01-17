package com.example.weatherapplgctakehome.data

import com.example.weatherapplgctakehome.data.entities.weatherByLocation.GetWeatherByLocationApiResponse
import com.example.weatherapplgctakehome.domain.WeatherRepository
import com.example.weatherapplgctakehome.domain.model.WeatherData
import retrofit2.Response

class WeatherRepositoryImpl : WeatherRepository {
    override suspend fun getCurrentWeatherByLocation(
        apiKey: String,
        location: String
    ): Response<GetWeatherByLocationApiResponse> {
        val apiResponse = RetrofitClient.apiInterface.getWeatherByLocation(apiKey, location)
        return apiResponse
    }
}