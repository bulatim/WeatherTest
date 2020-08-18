package ru.bulat.weatherapplicationtest.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import ru.bulat.weatherapplicationtest.R
import ru.bulat.weatherapplicationtest.ui.weather.ListWeatherFragment
import ru.bulat.weatherapplicationtest.ui.weather.MapWeatherFragment


class MainActivity : AppCompatActivity() {
    lateinit var listWeatherFragment: ListWeatherFragment
    lateinit var mapWeatherFragment: MapWeatherFragment
    lateinit var active: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.fragments.size == 0) {
            listWeatherFragment = ListWeatherFragment()
            mapWeatherFragment = MapWeatherFragment()

            supportFragmentManager.beginTransaction()
                .add(
                    R.id.nav_host_container,
                    mapWeatherFragment,
                    MapWeatherFragment::class.java.simpleName
                )
                .hide(mapWeatherFragment)
                .commit()
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.nav_host_container,
                    listWeatherFragment,
                    ListWeatherFragment::class.java.simpleName
                )
                .commit()
            active = listWeatherFragment
        } else {
            val activeFragment = savedInstanceState?.getString("activeFragment")
            supportFragmentManager.fragments.forEach {
                if (activeFragment != null)
                    if (it.javaClass.simpleName == activeFragment)
                        active = it
                when (it) {
                    is ListWeatherFragment -> listWeatherFragment = it
                    is MapWeatherFragment -> mapWeatherFragment = it
                }
            }
        }

        bottom_nav.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener when (it.itemId) {
                R.id.list_weather -> {
                    supportFragmentManager
                        .beginTransaction()
                        .hide(active)
                        .show(listWeatherFragment)
                        .commit()
                    active = listWeatherFragment
                    true
                }
                R.id.map_weather -> {
                    supportFragmentManager
                        .beginTransaction()
                        .hide(active)
                        .show(mapWeatherFragment)
                        .commit()
                    active = mapWeatherFragment
                    true
                }
                else -> false
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.fragments.forEach {
            if (it.isVisible)
                outState.putString("activeFragment", it.javaClass.simpleName)
        }
    }
}
