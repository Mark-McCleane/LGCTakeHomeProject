package com.example.weatherapplgctakehome.data

import com.example.weatherapplgctakehome.data.entities.astronomyByLocation.GetAstronomyByLocationApiResult
import com.example.weatherapplgctakehome.data.entities.weatherByLocation.GetWeatherByLocationApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface WeatherApiInterface {

    @GET("current.json")
    suspend fun getWeatherByLocation(
        @Query("key") apiKey: String,
        @Query("q") location: String,
    ): Response<GetWeatherByLocationApiResponse>

//    https://api.weatherapi.com/v1/astronomy.json?q=wexford&dt=2025-01-17&key=bcaf6500ac794e0eb16172753251701

    @GET("astronomy.json")
    suspend fun getAstronomyByLocation(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("dt") date: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    ): Response<GetAstronomyByLocationApiResult>
}
