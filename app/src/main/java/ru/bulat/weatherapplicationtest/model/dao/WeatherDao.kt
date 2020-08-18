package ru.bulat.weatherapplicationtest.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.bulat.weatherapplicationtest.model.entities.Weather

@Dao
interface WeatherDao {
    @get:Query("SELECT * FROM `Weather`")
    val all: LiveData<List<Weather>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weather: Weather): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(weathers: List<Weather>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg weather: Weather): Int

    @Query("SELECT * FROM `Weather` WHERE id=:id")
    fun getById(id: Long): Weather

    @Query("SELECT * FROM `Weather` WHERE latitude=:latitude AND longtitude=:longtitude")
    fun getByCoordinate(latitude: Double, longtitude: Double): Weather?

    @Query("DELETE FROM `Weather` WHERE id=:id")
    fun deleteById(id: Long)
}