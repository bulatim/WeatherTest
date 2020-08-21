package ru.bulat.weatherapplicationtest.ui.weather.base

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import ru.bulat.weatherapplicationtest.model.api.response.WeatherResponse
import ru.bulat.weatherapplicationtest.model.entities.Weather
import ru.bulat.weatherapplicationtest.repository.WeatherRepository
import ru.bulat.weatherapplicationtest.utils.iconResource
import ru.bulat.weatherapplicationtest.utils.iconString
import ru.bulat.weatherapplicationtest.utils.temperatureCelsius
import java.net.UnknownHostException
import java.util.*
import javax.inject.Inject

open class BaseWeatherViewModel : ViewModel() {
    @Inject
    lateinit var weatherRepository: WeatherRepository
    @Inject
    lateinit var gson: Gson
    val currentIconImage = MutableLiveData<Int>()
    val currentTemperature = MutableLiveData<String>()
    val currentDescription = MutableLiveData<String>()

    val loadingVisibilityProgress: MutableLiveData<Int> = MutableLiveData()
    val loadingVisibilityGroup: MutableLiveData<Int> = MutableLiveData()

    val errorLiveData = MutableLiveData<String>()

    fun onStartLoading() {
        loadingVisibilityProgress.value = View.VISIBLE
        loadingVisibilityGroup.value = View.GONE
    }

    fun onFinishLoading() {
        loadingVisibilityProgress.postValue(View.GONE)
        loadingVisibilityGroup.postValue(View.VISIBLE)
    }

    suspend fun loadWeatherByCoordinate(latitude: Double, longtitude: Double): WeatherResponse? {
        val weather = weatherRepository.getWeatherByCoordinateLocal(latitude, longtitude)
        val currentDate = Date()
        var weatherResponse: WeatherResponse? = null
        if (weather == null || ((currentDate.time - weather.date!!.time) / (60 * 1000) > 60)) {
            if (weather != null)
                weatherRepository.deleteWeatherById(weather.id!!)
            try {
                weatherResponse =
                    weatherRepository.getWeatherByCoordinateCloude(latitude, longtitude).await()
                weatherRepository.insertWeather(Weather().apply {
                    this.date = Date()
                    this.latitude = latitude
                    this.longtitude = longtitude
                    this.data = gson.toJson(weatherResponse)
                })
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException -> errorLiveData.value =
                        "Ошибка. Проверьте наличие сети"
                    else -> errorLiveData.value = "Неизвестная ошибка"
                }
                onFinishLoading()
                e.printStackTrace()
            }
        } else
            weatherResponse = gson.fromJson(weather.data, WeatherResponse::class.java)

        currentTemperature.value = weatherResponse?.currently?.temperatureCelsius()
        currentDescription.value = weatherResponse?.currently?.iconString()
        currentIconImage.value = weatherResponse?.currently?.iconResource()

        return weatherResponse
    }
}