package ru.bulat.weatherapplicationtest.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import ru.bulat.weatherapplicationtest.model.api.response.WeatherResponse

interface WeatherApi {
    @GET("/forecast/3e7e519ea86c8e3fcf67c0f4870513d7/{longtitude},{latitude}/")
    fun getWeatherByCoordinate(@Path("longtitude") longtitude: Double,
                               @Path("latitude") latitude: Double): Deferred<WeatherResponse>
}