package ru.bulat.weatherapplicationtest.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.bulat.weatherapplicationtest.model.converter.Converters
import ru.bulat.weatherapplicationtest.model.dao.WeatherDao
import ru.bulat.weatherapplicationtest.model.entities.Weather

@Database(entities = [Weather::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}