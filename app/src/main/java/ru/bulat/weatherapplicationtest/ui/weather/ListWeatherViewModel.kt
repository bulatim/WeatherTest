package ru.bulat.weatherapplicationtest.ui.weather

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.bulat.weatherapplicationtest.ui.weather.base.BaseWeatherViewModel
import javax.inject.Inject

class ListWeatherViewModel @Inject constructor(): BaseWeatherViewModel() {
    lateinit var recyclerViewUpdateCallback: () -> Unit
    val dayWeatherListAdapter: WeatherListAdapter = WeatherListAdapter()

    fun getWeatherByCoordinate(latitude: Double, longtitude: Double) {
        viewModelScope.launch {
            onStartLoading()
            val weatherResponse = loadWeatherByCoordinate(latitude, longtitude)
            weatherResponse?.daily?.data?.let {
                dayWeatherListAdapter.updateDayWeatherList(it)
                recyclerViewUpdateCallback.invoke()
            }
            onFinishLoading()
        }
    }
}