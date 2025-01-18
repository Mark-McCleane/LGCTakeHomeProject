package com.example.weatherapplgctakehome.domain

import com.example.weatherapplgctakehome.data.entities.astronomyByLocation.GetAstronomyByLocationApiResult
import com.example.weatherapplgctakehome.data.entities.weatherByLocation.GetWeatherByLocationApiResponse
import com.example.weatherapplgctakehome.domain.model.AstronomyData
import com.example.weatherapplgctakehome.domain.model.WeatherData

interface WeatherDataMapper {
    fun mapWeatherResponseToData(original: GetWeatherByLocationApiResponse) : WeatherData
    fun mapAstronomyResponseToData(original: GetAstronomyByLocationApiResult) : AstronomyData
}