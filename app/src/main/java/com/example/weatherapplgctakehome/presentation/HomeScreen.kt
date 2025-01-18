package com.example.weatherapplgctakehome.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.weatherapplgctakehome.data.WeatherRepositoryImpl
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: WeatherViewModel = koinViewModel(),
    navigateTo: (locationName: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val searchText by viewModel.searchText.collectAsState()
    val currentWeatherData by viewModel.currentWeatherData.collectAsState()
    val currentWeatherDataList by viewModel.currentWeatherDataList.collectAsState()
    var isCelsius by rememberSaveable { mutableStateOf(true) }
    val error by viewModel.error.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.updateTitle("Weather App")
    }

    LaunchedEffect(error) {
        if (error != null) {
            snackbarHostState.showSnackbar(
                "Error: $error",
                withDismissAction = true,
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    Column {
        TextField(
            value = searchText,
            onValueChange = viewModel::onSearchTextChange,
            modifier = modifier.fillMaxWidth(),
            placeholder = { Text(text = "Search Tv Show") },
            trailingIcon = {
                IconButton(
                    onClick = {
                        viewModel.getCurrentWeatherByLocation(searchText)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Tv Shows"
                    )
                }
            }
        )

        if (currentWeatherDataList.isNotEmpty()) {
            LazyColumn {
                items(currentWeatherDataList) { weatherData ->
                    ListItem(
                        leadingContent = {
                            AsyncImage(
                                model = "https:${weatherData?.weatherCondition?.icon}",
                                contentDescription = "Weather Icon Image For: ${weatherData?.weatherCondition?.text}",
                                contentScale = ContentScale.Crop,
                                error = painterResource(android.R.drawable.stat_notify_error),
                                modifier = Modifier.size(50.dp)
                            )
                        },
                        headlineContent = {
                            Text(
                                weatherData?.weatherCondition?.text ?: "",
                                modifier = Modifier.weight(1f)
                            )
                        },
                        supportingContent = {
                            Text(
                                text = weatherData?.locationName ?: "",
                                modifier = Modifier.weight(1f)
                            )
                        },
                        trailingContent = {
                            Text(
                                text = if (isCelsius) "${weatherData?.currentTempInCelsius} °C" else "${weatherData?.currentTempInFahrenheit} °F",
                                modifier = Modifier.clickable { isCelsius = !isCelsius }
                            )
                        },
                        modifier = Modifier.clickable {
                            navigateTo(weatherData?.locationName ?: "")
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val repo = WeatherRepositoryImpl()
        HomeScreen(
            viewModel = WeatherViewModel(repo),
            navigateTo = {},
            modifier = Modifier.padding(innerPadding)
        )
    }
}