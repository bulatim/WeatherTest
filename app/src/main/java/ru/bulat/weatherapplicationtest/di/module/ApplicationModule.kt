package ru.bulat.weatherapplicationtest.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.Gson
import ru.bulat.weatherapplicationtest.di.scope.AppScope
import dagger.Module
import dagger.Provides
import ru.bulat.weatherapplicationtest.model.database.AppDatabase
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class ApplicationModule {

    @AppScope
    @Provides
    internal fun provideDatabase(context: Context): AppDatabase {
        val db = Room.databaseBuilder(context, AppDatabase::class.java, "application_database")
            .build()
        return db
    }

    @AppScope
    @Provides
    internal fun provideFiscalDao(database: AppDatabase) = database.weatherDao()

    @AppScope
    @Provides
    internal fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("${context.packageName}_preferences",
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    internal fun provideGson() = Gson()
}