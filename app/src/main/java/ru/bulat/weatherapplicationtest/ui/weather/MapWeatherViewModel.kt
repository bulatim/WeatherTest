package ru.bulat.weatherapplicationtest.ui.weather

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.bulat.weatherapplicationtest.ui.weather.base.BaseWeatherViewModel
import javax.inject.Inject

class MapWeatherViewModel @Inject constructor() : BaseWeatherViewModel() {

    fun getWeatherByCoordinate(latitude: Double, longtitude: Double) {
        viewModelScope.launch {
            onStartLoading()
            loadWeatherByCoordinate(latitude, longtitude)
            onFinishLoading()
        }
    }
}