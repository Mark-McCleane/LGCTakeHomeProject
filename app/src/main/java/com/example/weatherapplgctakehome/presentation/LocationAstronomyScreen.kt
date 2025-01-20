package com.example.weatherapplgctakehome.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun LocationAstronomyScreen(
    viewModel: WeatherViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    locationName: String = "",
    navController: NavHostController
) {
    val state by viewModel.uiState.collectAsState()

    BackHandler {
        viewModel.updateBackIconVisibility(false)
        navController.popBackStack()
    }

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
        state.currentAstronomyData?.let { astroData ->
            val astroItems = listOf(
                AstroItem("Sun Rise", astroData.sunRise),
                AstroItem("Sun Set", astroData.sunSet),
                AstroItem("Moon Rise", astroData.moonRise),
                AstroItem("Moon Set", astroData.moonSet),
                AstroItem("Moon Phase", astroData.moonPhase),
                AstroItem("Moon Illumination", astroData.moonIllumination.toString()),
                AstroItem(
                    label = "Is Sun Up",
                    value = astroData.isSunUp.toString().replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                    }
                ),
                AstroItem(
                    label = "Is Moon Up",
                    value = astroData.isMoonUp.toString().replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                    }
                ),
            )

            LazyColumn(modifier = modifier) {
                items(astroItems.size) { index ->
                    ListItem(
                        headlineContent = { Text(astroItems[index].label) },
                        trailingContent = { Text(astroItems[index].value) }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

data class AstroItem(
    val label: String,
    val value: String
)