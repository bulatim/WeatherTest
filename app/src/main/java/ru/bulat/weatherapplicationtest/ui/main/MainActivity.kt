package ru.bulat.weatherapplicationtest.ui.main

import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
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
            createShortcuts()

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
            if (intent.getStringExtra("action") == "map") {
                showFragment(mapWeatherFragment)
                bottom_nav.selectedItemId = R.id.map_weather
            }
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
                    showFragment(listWeatherFragment)
                    true
                }
                R.id.map_weather -> {
                    showFragment(mapWeatherFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out)
            .hide(active)
            .show(fragment)
            .commit()
        active = fragment
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.fragments.forEach {
            if (it.isVisible)
                outState.putString("activeFragment", it.javaClass.simpleName)
        }
    }

    private fun createShortcuts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val shortcutManager = getSystemService(
                ShortcutManager::class.java
            )

            val listShortcut = ShortcutInfo.Builder(this, "shortcut_list")
                .setShortLabel("Неделя")
                .setLongLabel("Погода на неделю")
                .setIcon(Icon.createWithResource(this, R.drawable.ic_view_list_black_24dp))
                .setIntents(
                    arrayOf(
                        Intent(
                            Intent.ACTION_MAIN,
                            Uri.EMPTY,
                            this,
                            MainActivity::class.java
                        ).setFlags(
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                        ).putExtra("action", "list")
                    )
                )
                .build()

            val mapShortcut = ShortcutInfo.Builder(this, "shortcut_map")
                .setShortLabel("На карте")
                .setLongLabel("Погода на карте")
                .setIcon(Icon.createWithResource(this, R.drawable.ic_map_black_24dp))
                .setIntents(
                    arrayOf(
                        Intent(
                            Intent.ACTION_MAIN,
                            Uri.EMPTY,
                            this,
                            MainActivity::class.java
                        ).setFlags(
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                        ).putExtra("action", "map")
                    )
                )
                .build()

            shortcutManager?.dynamicShortcuts = listOf(listShortcut, mapShortcut)
        }
    }
}
