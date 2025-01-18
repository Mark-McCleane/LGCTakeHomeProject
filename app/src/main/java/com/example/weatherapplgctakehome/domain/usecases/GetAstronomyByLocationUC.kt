package com.example.weatherapplgctakehome.domain.usecases

import com.example.weatherapplgctakehome.data.WeatherDataMapperImpl
import com.example.weatherapplgctakehome.domain.WeatherRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GetAstronomyByLocationUC(private val repository: WeatherRepository) {

    suspend operator fun invoke(
        locationParam: String,
        dateTime: String = LocalDate.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        )
    ): Any {
        val apiKey = "bcaf6500ac794e0eb16172753251701"  //System.getProperty("API_KEY") ?: ""
        val data = repository.getAstronomyByLocation(apiKey, locationParam, dateTime)
        if (data.isSuccessful && data.body() != null) {
            val mapper = WeatherDataMapperImpl()
            val astronomyData =
                mapper.mapAstronomyResponseToData(data.body()!!) // Safe to unwrap as checked above
            return astronomyData
        } else {
            // Handle errors
            return data.message() ?: "Unknown Error"
        }
    }
}
