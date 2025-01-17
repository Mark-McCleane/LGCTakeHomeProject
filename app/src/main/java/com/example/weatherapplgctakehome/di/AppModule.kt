package com.example.weatherapplgctakehome.di

import com.example.weatherapplgctakehome.data.WeatherRepositoryImpl
import com.example.weatherapplgctakehome.domain.WeatherRepository
import com.example.weatherapplgctakehome.presentation.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class AppModule {
    companion object {
        val mainModule = module {
            single<WeatherRepository> { WeatherRepositoryImpl() }
            viewModel { WeatherViewModel(get()) }
        }
    }
}