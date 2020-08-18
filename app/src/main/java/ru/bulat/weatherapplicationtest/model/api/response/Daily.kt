package ru.bulat.weatherapplicationtest.model.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Daily {
    @SerializedName("summary")
    @Expose
    val summary: String? = null
    @SerializedName("icon")
    @Expose
    val icon: String? = null
    @SerializedName("data")
    @Expose
    val data: List<Datum_>? = null
}