package ru.bulat.weatherapplicationtest.ui.weather

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import ru.bulat.weatherapplicationtest.R
import ru.bulat.weatherapplicationtest.model.api.response.WeatherResponse
import ru.bulat.weatherapplicationtest.model.entities.Weather
import ru.bulat.weatherapplicationtest.repository.WeatherRepository
import ru.bulat.weatherapplicationtest.utils.fahrenheitToCelsius
import java.util.*
import javax.inject.Inject

class ListWeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    lateinit var recyclerViewUpdateCallback: () -> Unit
    val dayWeatherListAdapter: WeatherListAdapter = WeatherListAdapter()
    val image = MutableLiveData<Int>()
    val currentTemperature = MutableLiveData<String>()
    val currentDescription = MutableLiveData<String>()
    val loadingVisibilityProgress: MutableLiveData<Int> = MutableLiveData()
    val loadingVisibilityGroup: MutableLiveData<Int> = MutableLiveData()
    val gson = Gson()

    fun getWeatherByCoordinate(latitude: Double, longtitude: Double) {
        viewModelScope.launch {
            onStartLoading()
            val weather = weatherRepository.getWeatherByCoordinateLocal(latitude, longtitude)
            val currentDate = Date()
            val weatherResponse: WeatherResponse
            if (weather == null || ((currentDate.time - weather.date!!.time) / (60 * 1000) > 60)) {
                if (weather != null)
                    weatherRepository.deleteWeatherById(weather.id!!)
                weatherResponse =
                    weatherRepository.getWeatherByCoordinateCloude(latitude, longtitude).await()
                weatherRepository.insertWeather(Weather().apply {
                    this.date = Date()
                    this.latitude = latitude
                    this.longtitude = longtitude
                    this.data = gson.toJson(weatherResponse)
                })
            } else
                weatherResponse = gson.fromJson(weather.data, WeatherResponse::class.java)
            weatherResponse.currently?.temperature?.let {
                currentTemperature.value = fahrenheitToCelsius(it)
            }
            weatherResponse.currently?.icon?.let {
                currentDescription.value = when (it) {
                    "clear-day" -> "ЯСНО"
                    "rain" -> "ДОЖДЬ"
                    "cloudy" -> "ОБЛАЧНО"
                    "partly-cloudy-day" -> "ПЕРЕМЕННАЯ ОБЛАЧНОСТЬ"
                    else -> "ЯСНО"
                }
                image.value = when(it) {
                    "clear-day" -> R.drawable.clearday
                    "rain" -> R.drawable.rain
                    "cloudy" -> R.drawable.cloudy
                    "partly-cloudy-day" -> R.drawable.partly_cloudy_day
                    else -> R.drawable.clearday
                }
            }
            weatherResponse.daily?.data?.let {
                dayWeatherListAdapter.updateDayWeatherList(it)
                recyclerViewUpdateCallback.invoke()
            }
            onFinishLoading()
        }
    }

    fun onStartLoading() {
        loadingVisibilityProgress.value = View.VISIBLE
        loadingVisibilityGroup.value = View.GONE
    }

    fun onFinishLoading() {
        loadingVisibilityProgress.postValue(View.GONE)
        loadingVisibilityGroup.postValue(View.VISIBLE)
    }
}