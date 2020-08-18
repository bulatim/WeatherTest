package ru.bulat.weatherapplicationtest.ui.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.bulat.weatherapplicationtest.R
import ru.bulat.weatherapplicationtest.model.api.response.Datum_
import ru.bulat.weatherapplicationtest.utils.convertDateToString

class DayWeatherViewModel: ViewModel() {
    val image = MutableLiveData<Int>()
    val temperature = MutableLiveData<String>()
    val day = MutableLiveData<String>()

    fun bind(weatherDate: Datum_) {
        weatherDate.apparentTemperatureHigh?.let {
            temperature.value = fahrenheitToCelsius(it)
        }
        weatherDate.time?.let {
            day.value = convertDateToString(it)
        }
        weatherDate.icon?.let {
            image.value = when(it) {
                "clear-day" -> R.drawable.clearday
                "rain" -> R.drawable.rain
                "cloudy" -> R.drawable.cloudy
                "partly-cloudy-day" -> R.drawable.partly_cloudy_day
                else -> R.drawable.clearday
            }
        }
    }

    private fun fahrenheitToCelsius(fahrenheit: Double): String {
        val celsius = "${((fahrenheit - 32) * (5f / 9)).toInt()}°C"
        return celsius
    }
}