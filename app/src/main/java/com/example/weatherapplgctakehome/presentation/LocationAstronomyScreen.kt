package com.example.weatherapplgctakehome.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun LocationAstronomyScreen(
    viewModel: WeatherViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    locationName: String = ""
) {
    val astronomyData by viewModel.currentAstronomyData.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getAstronomyByLocation(locationName)
        viewModel.updateTitle("Astronomy Data For $locationName")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        astronomyData?.let {
            AstronomyItem(label = "Sun Rise", value = it.sunRise)
            AstronomyItem(label = "Sun Set", value = it.sunSet)
            AstronomyItem(label = "Moon Rise", value = it.moonRise)
            AstronomyItem(label = "Moon Set", value = it.moonSet)
            AstronomyItem(label = "Moon Phase", value = it.moonPhase)
            AstronomyItem(label = "Moon Illumination", value = it.moonIllumination.toString())
            AstronomyItem(label = "Is Sun Up", value = if (it.isSunUp) "Yes" else "No")
            AstronomyItem(label = "Is Moon Up", value = if (it.isMoonUp) "Yes" else "No")
        }
    }
}

@Composable
fun AstronomyItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Bold)
        Text(text = value)
    }
    Spacer(modifier = Modifier.height(4.dp))
}