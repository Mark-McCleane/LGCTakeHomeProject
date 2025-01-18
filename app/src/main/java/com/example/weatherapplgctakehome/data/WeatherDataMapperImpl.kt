package com.example.weatherapplgctakehome.data

import com.example.weatherapplgctakehome.data.entities.astronomyByLocation.GetAstronomyByLocationApiResult
import com.example.weatherapplgctakehome.data.entities.weatherByLocation.GetWeatherByLocationApiResponse
import com.example.weatherapplgctakehome.domain.WeatherDataMapper
import com.example.weatherapplgctakehome.domain.model.AstronomyData
import com.example.weatherapplgctakehome.domain.model.WeatherCondition
import com.example.weatherapplgctakehome.domain.model.WeatherData

class WeatherDataMapperImpl : WeatherDataMapper  {
    override fun mapWeatherResponseToData(original: GetWeatherByLocationApiResponse): WeatherData {
        original.apply {
            return WeatherData(
                locationName = location.name,
                localTime = location.localtime,
                currentTempInCelsius = current.temp_c,
                currentTempInFahrenheit = current.temp_f,
                weatherCondition = WeatherCondition(
                    text = current.condition.text,
                    icon = current.condition.icon,
                    code = current.condition.code
                )
            )
        }
    }

    override fun mapAstronomyResponseToData(original: GetAstronomyByLocationApiResult): AstronomyData {
        original.apply {
            return AstronomyData(
                sunRise = astronomy.astro.sunrise,
                sunSet = astronomy.astro.sunset,
                moonRise = astronomy.astro.moonrise,
                moonSet = astronomy.astro.moonset,
                moonPhase = astronomy.astro.moon_phase,
                moonIllumination = astronomy.astro.moon_illumination,
            )
        }
    }
}