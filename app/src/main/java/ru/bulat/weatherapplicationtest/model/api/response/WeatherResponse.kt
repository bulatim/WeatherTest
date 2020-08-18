package ru.bulat.weatherapplicationtest.model.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class WeatherResponse {
    @SerializedName("latitude")
    @Expose
    val latitude: Double? = null
    @SerializedName("longitude")
    @Expose
    val longitude: Double? = null
    @SerializedName("timezone")
    @Expose
    val timezone: String? = null
    @SerializedName("currently")
    @Expose
    val currently: Currently? = null
    @SerializedName("hourly")
    @Expose
    val hourly: Hourly? = null
    @SerializedName("daily")
    @Expose
    val daily: Daily? = null
    @SerializedName("flags")
    @Expose
    val flags: Flags? = null
    @SerializedName("offset")
    @Expose
    val offset: Double? = null
}