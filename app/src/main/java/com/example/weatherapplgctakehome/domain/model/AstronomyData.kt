package com.example.weatherapplgctakehome.domain.model

data class AstronomyData(
    val sunRise: String = "",
    val sunSet: String = "",
    val moonRise: String = "",
    val moonSet: String = "",
    val moonPhase: String = "",
    val moonIllumination: Int = 0,
    val isMoonUp: Boolean = false,
    val isSunUp: Boolean = false,
)