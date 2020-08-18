package ru.bulat.weatherapplicationtest.di.module

import android.content.Context
import ru.bulat.weatherapplicationtest.di.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
class ContextModule constructor(private val context: Context) {

    @AppScope
    @Provides
    internal fun provideContext(): Context = context
}