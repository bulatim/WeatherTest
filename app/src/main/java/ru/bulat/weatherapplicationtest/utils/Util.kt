package ru.bulat.weatherapplicationtest.utils

import android.annotation.SuppressLint
import ru.bulat.weatherapplicationtest.R
import ru.bulat.weatherapplicationtest.model.api.response.Currently
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
val simpleDateFormatEddMMMM = SimpleDateFormat("E, dd MMMM")

fun fahrenheitToCelsius(fahrenheit: Double?) = if (fahrenheit != null)
    "${((fahrenheit - 32) * (5f / 9)).toInt()}°C"
else ""

fun convertDateToString(time: Int) =
    simpleDateFormatEddMMMM.format(Date(time.toLong() * 1000))

fun Currently.temperatureCelsius() = fahrenheitToCelsius(this.temperature)

fun Currently.iconString() = when (this.icon) {
    "clear-day" -> "ЯСНО"
    "rain" -> "ДОЖДЬ"
    "cloudy" -> "ОБЛАЧНО"
    "partly-cloudy-day" -> "ПЕРЕМЕННАЯ ОБЛАЧНОСТЬ"
    else -> "ЯСНО"
}

fun Currently.iconResource() = when (this.icon) {
    "clear-day" -> R.drawable.clearday
    "rain" -> R.drawable.rain
    "cloudy" -> R.drawable.cloudy
    "partly-cloudy-day" -> R.drawable.partly_cloudy_day
    else -> R.drawable.clearday
}
