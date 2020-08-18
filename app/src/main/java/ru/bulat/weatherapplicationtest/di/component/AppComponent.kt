package ru.bulat.weatherapplicationtest.di.component

import ru.bulat.weatherapplicationtest.di.module.ApplicationModule
import ru.bulat.weatherapplicationtest.di.module.NetworkModule
import ru.bulat.weatherapplicationtest.di.module.ViewModelModule
import ru.bulat.weatherapplicationtest.di.scope.AppScope
import dagger.Component
import ru.bulat.weatherapplicationtest.ui.main.MainActivity
import ru.bulat.weatherapplicationtest.ui.weather.ListWeatherFragment
import ru.bulat.weatherapplicationtest.ui.weather.MapWeatherFragment
import javax.inject.Singleton

@AppScope
@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: MapWeatherFragment)
    fun inject(fragment: ListWeatherFragment)
}