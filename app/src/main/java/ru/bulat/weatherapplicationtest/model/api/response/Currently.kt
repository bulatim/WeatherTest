package ru.bulat.weatherapplicationtest.model.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Currently {
    @SerializedName("time")
    @Expose
    val time: Int? = null
    @SerializedName("summary")
    @Expose
    val summary: String? = null
    @SerializedName("icon")
    @Expose
    val icon: String? = null
    @SerializedName("precipIntensity")
    @Expose
    val precipIntensity: Double? = null
    @SerializedName("precipProbability")
    @Expose
    val precipProbability: Double? = null
    @SerializedName("temperature")
    @Expose
    val temperature: Double? = null
    @SerializedName("apparentTemperature")
    @Expose
    val apparentTemperature: Double? = null
    @SerializedName("dewPoint")
    @Expose
    val dewPoint: Double? = null
    @SerializedName("humidity")
    @Expose
    val humidity: Double? = null
    @SerializedName("pressure")
    @Expose
    val pressure: Double? = null
    @SerializedName("windSpeed")
    @Expose
    val windSpeed: Double? = null
    @SerializedName("windGust")
    @Expose
    val windGust: Double? = null
    @SerializedName("windBearing")
    @Expose
    val windBearing: Int? = null
    @SerializedName("cloudCover")
    @Expose
    val cloudCover: Double? = null
    @SerializedName("uvIndex")
    @Expose
    val uvIndex: Int? = null
    @SerializedName("visibility")
    @Expose
    val visibility: Double? = null
    @SerializedName("ozone")
    @Expose
    val ozone: Double? = null
}