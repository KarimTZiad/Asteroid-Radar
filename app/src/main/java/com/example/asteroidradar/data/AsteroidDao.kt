package com.example.asteroidradar.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.asteroidradar.Asteroid
import com.example.asteroidradar.PictureOfDay

@Dao
interface AsteroidDao {
    @Query("SELECT * FROM Asteroid ORDER BY closeApproachDate ASC")
    fun readAllAsteroids() : LiveData<List<Asteroid>>

    @Query("SELECT * FROM Asteroid WHERE (closeApproachDate >= :today AND closeApproachDate <= :endDate) ORDER BY closeApproachDate ASC")
    fun getWeekAsteroids(today: String, endDate: String) : LiveData<List<Asteroid>>

    @Query("SELECT * FROM Asteroid WHERE (closeApproachDate == :today) ORDER BY id ASC")
    fun getTodayAsteroids(today: String): LiveData<List<Asteroid>>

    @Query("SELECT * FROM Asteroid WHERE id = :asteroidId")
    fun getAsteroid(asteroidId: Long) : LiveData<Asteroid>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllAsteroids(vararg asteroid: Asteroid)

    @Query("DELETE FROM Asteroid")
    fun deleteAllAsteroids()

    @Query("DELETE FROM Asteroid WHERE(closeApproachDate < :today)")
    fun deleteOldAsteroids(today: String)

    @Query("SELECT * FROM PictureOfDay WHERE(mediaType =='image') ORDER BY date DESC LIMIT 1")
    fun getPictureOfDay() : LiveData<PictureOfDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePictureOfDay(picOfDay: PictureOfDay)

    @Query("DELETE FROM PictureOfDay WHERE(date < :today)")
    fun deleteOldPictureOfDay(today: String)
}