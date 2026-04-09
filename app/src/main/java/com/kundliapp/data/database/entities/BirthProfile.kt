package com.kundliapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "birth_profiles")
data class BirthProfile(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val gender: String,
    val birthDay: Int,
    val birthMonth: Int,
    val birthYear: Int,
    val birthHour: Int,
    val birthMinute: Int,
    val birthPlace: String,
    val latitude: Double,
    val longitude: Double,
    val sunSign: String = "",
    val moonSign: String = "",
    val ascendant: String = "",
    val nakshatra: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
