package ru.bulat.weatherapplicationtest

import androidx.multidex.MultiDexApplication
import ru.bulat.weatherapplicationtest.di.component.AppComponent
import ru.bulat.weatherapplicationtest.di.component.DaggerAppComponent
import ru.bulat.weatherapplicationtest.di.module.ContextModule

class App : MultiDexApplication() {
    lateinit var component: AppComponent
    lateinit var contextModule: ContextModule

    override fun onCreate() {
        super.onCreate()
        contextModule = ContextModule(this)
        component = DaggerAppComponent.builder()
            .contextModule(contextModule)
            .build()
    }
}