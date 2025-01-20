package com.example.weatherapplgctakehome.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.weatherapplgctakehome.ui.theme.WeatherAppLGCTakeHomeTheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            val navController = rememberNavController()
            val mainViewModel: WeatherViewModel = koinViewModel()
            val state by mainViewModel.uiState.collectAsState()
            WeatherAppLGCTakeHomeTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = state.title ?: "Weather App") },
                            navigationIcon = {
                                if (state.backIconVisible) {
                                    IconButton(onClick = {
                                        navController.popBackStack()
                                        mainViewModel.updateBackIconVisibility(false)
                                    }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Navigation Back Arrow",
                                        )
                                    }
                                }
                            }
                        )
                    },
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = HomeScreenRoute
                    ) {
                        composable<HomeScreenRoute> {
                            HomeScreen(
                                viewModel = mainViewModel,
                                navigateTo = { locationName ->
                                    navController.navigate(
                                        LocationAstronomyScreenRoute(
                                            locationName
                                        )
                                    )
                                    mainViewModel.updateBackIconVisibility(true)
                                },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }

                        composable<LocationAstronomyScreenRoute> { backStackEntry ->
                            val args =
                                backStackEntry.toRoute<LocationAstronomyScreenRoute>()

                            LocationAstronomyScreen(
                                viewModel = mainViewModel,
                                locationName = args.locationName,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Serializable
object HomeScreenRoute

@Serializable
data class LocationAstronomyScreenRoute(val locationName: String)