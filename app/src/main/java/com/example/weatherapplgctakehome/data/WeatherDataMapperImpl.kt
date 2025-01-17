package com.example.weatherapplgctakehome.data

import com.example.weatherapplgctakehome.data.entities.weatherByLocation.GetWeatherByLocationApiResponse
import com.example.weatherapplgctakehome.domain.WeatherDataMapper
import com.example.weatherapplgctakehome.domain.model.WeatherCondition
import com.example.weatherapplgctakehome.domain.model.WeatherData

class WeatherDataMapperImpl : WeatherDataMapper  {
    override fun map(original: GetWeatherByLocationApiResponse): WeatherData {
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
}