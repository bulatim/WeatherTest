package ru.bulat.weatherapplicationtest.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.bulat.weatherapplicationtest.base.ViewModelFactory
import ru.bulat.weatherapplicationtest.di.ViewModelKey
import ru.bulat.weatherapplicationtest.di.scope.AppScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.bulat.weatherapplicationtest.ui.weather.ListWeatherViewModel
import ru.bulat.weatherapplicationtest.ui.weather.MapWeatherViewModel

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ListWeatherViewModel::class)
    abstract fun bindWeatherViewModel(weatherViewModel: ListWeatherViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapWeatherViewModel::class)
    abstract fun bindMapWeatherViewModel(mapWeatherViewModel: MapWeatherViewModel): ViewModel

    @AppScope
    @Binds abstract
    fun bindViewModelFactory(vmFactory: ViewModelFactory): ViewModelProvider.Factory
}