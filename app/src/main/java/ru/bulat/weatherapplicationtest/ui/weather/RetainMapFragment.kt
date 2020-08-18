package ru.bulat.weatherapplicationtest.ui.weather

import android.os.Bundle
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.SupportMapFragment

class RetainMapFragment: SupportMapFragment()
{
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = true
    }
}