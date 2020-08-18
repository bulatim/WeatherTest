package ru.bulat.weatherapplicationtest.model.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Flags {
    @SerializedName("sources")
    @Expose
    val sources: List<String>? = null
    @SerializedName("nearest-station")
    @Expose
    val nearestStation: Double? = null
    @SerializedName("units")
    @Expose
    val units: String? = null
}