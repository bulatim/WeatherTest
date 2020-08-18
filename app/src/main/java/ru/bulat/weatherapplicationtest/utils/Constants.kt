package ru.bulat.weatherapplicationtest.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

const val BASE_URL = "https://api.darksky.net/"
const val REQUEST_CODE_PERMISSION_ACCESS_FINE_LOCATION = 100

@SuppressLint("SimpleDateFormat")
val simpleDateFormatEddMMMM = SimpleDateFormat("E, dd MMMM")

fun fahrenheitToCelsius(fahrenheit: Double) =
    "${((fahrenheit - 32) * (5f / 9)).toInt()}°C"

fun convertDateToString(time: Int) =
    simpleDateFormatEddMMMM.format(Date(time.toLong() * 1000))
