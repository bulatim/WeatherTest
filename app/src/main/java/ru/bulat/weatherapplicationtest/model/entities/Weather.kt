package ru.bulat.weatherapplicationtest.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class Weather {
    @field:PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var date: Date? = null
    var latitude: Double = 0.0
    var longtitude: Double = 0.0
    var data: String = ""
}