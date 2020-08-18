package ru.bulat.weatherapplicationtest.utils

import android.location.Location
import android.location.LocationListener
import android.os.Bundle

class LocationListenerImpl(private val locationCallback: (location: Location?) -> Unit): LocationListener {
    override fun onLocationChanged(location: Location?) {
        locationCallback.invoke(location)
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

    }

    override fun onProviderEnabled(p0: String?) {

    }

    override fun onProviderDisabled(p0: String?) {

    }
}