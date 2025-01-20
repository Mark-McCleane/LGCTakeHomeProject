package com.example.weatherapplgctakehome.presentation

import com.example.weatherapplgctakehome.domain.WeatherRepository
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@ExperimentalCoroutinesApi
class WeatherViewModelTest {
    private lateinit var repository: WeatherRepository
    private lateinit var testViewModel: WeatherViewModel
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    class MainDispatcherRule(private val dispatcher: TestDispatcher = StandardTestDispatcher()) :
        TestWatcher() {
        override fun starting(description: Description?) = Dispatchers.setMain(dispatcher)

        override fun finished(description: Description?) = Dispatchers.resetMain()

    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testModules = module {
        single<WeatherRepository> { mockk() }
        viewModel { WeatherViewModel(get()) }
    }


    @BeforeEach
    fun setUp() {
        startKoin {
            modules(testModules)
        }
        repository = mockk()
        testViewModel = mockk()
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun onSearchTextChange() = runTest {
        val testSearchText = "Dublin"
        testViewModel.onSearchTextChange(testSearchText)
        val state = testViewModel.uiState.first()
        assert(state.searchText == testSearchText)
    }

    @Test
    fun getCurrentWeatherByLocation() {
    }

    @Test
    fun getAstronomyByLocation() {
    }

    @Test
    fun updateTitle() = runTest {
        val title = "New Title"
        testViewModel.updateTitle(title)
        val uiState = testViewModel.uiState.first()
        assert(uiState.title == title)
    }

    @Test
    fun updateBackIconVisibility() = runTest {
        val backIconVisible = true
        testViewModel.updateBackIconVisibility(backIconVisible)
        val uiState = testViewModel.uiState.first()
        assert(uiState.backIconVisible == backIconVisible)
    }
}