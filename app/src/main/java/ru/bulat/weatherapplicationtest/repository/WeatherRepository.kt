package ru.bulat.weatherapplicationtest.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.bulat.weatherapplicationtest.model.dao.WeatherDao
import ru.bulat.weatherapplicationtest.model.entities.Weather
import ru.bulat.weatherapplicationtest.network.WeatherApi
import java.time.LocalDate
import java.time.Period
import java.util.*
import javax.inject.Inject

class WeatherRepository @Inject
internal constructor(private val weatherApi: WeatherApi,
                     private val weatherDao: WeatherDao) {

    suspend fun getWeatherByCoordinateCloude(latitude: Double, longtitude: Double) = withContext(Dispatchers.IO) {
        weatherApi.getWeatherByCoordinate(latitude, longtitude)
    }

    suspend fun getWeatherByCoordinateLocal(latitude: Double, longtitude: Double) = withContext(Dispatchers.IO) {
        weatherDao.getByCoordinate(latitude, longtitude)
    }

    suspend fun deleteWeatherById(id: Long) = withContext(Dispatchers.IO) {
        weatherDao.deleteById(id)
    }

    suspend fun insertWeather(weather: Weather) = withContext(Dispatchers.IO) {
        weatherDao.insert(weather)
    }
}