package com.example.weatherapplgctakehome.data

import com.example.weatherapplgctakehome.data.entities.astronomyByLocation.GetAstronomyByLocationApiResult
import com.example.weatherapplgctakehome.data.entities.weatherByLocation.GetWeatherByLocationApiResponse
import com.example.weatherapplgctakehome.domain.WeatherRepository
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WeatherRepositoryImpl : WeatherRepository {
    override suspend fun getCurrentWeatherByLocation(
        apiKey: String,
        location: String
    ): Response<GetWeatherByLocationApiResponse> {
        val apiResponse = RetrofitClient.apiInterface.getWeatherByLocation(apiKey, location)
        return apiResponse
    }

    override suspend fun getAstronomyByLocation(
        apiKey: String,
        location: String,
        dateTime: String
    ): Response<GetAstronomyByLocationApiResult> {
        val apiResponse =
            RetrofitClient.apiInterface.getAstronomyByLocation(apiKey, location, dateTime)
        return apiResponse
    }
}