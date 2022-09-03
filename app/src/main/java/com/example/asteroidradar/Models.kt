package com.example.asteroidradar

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import com.squareup.moshi.Json

@Parcelize
@Entity
data class Asteroid(
    @PrimaryKey val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
    ) : Parcelable

@Entity
data class PictureOfDay(
    @PrimaryKey val date: String,
    @Json(name = "media_type") val mediaType: String,
    val title: String,
    val url: String
    )