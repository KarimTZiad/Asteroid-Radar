package com.example.asteroidradar.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.asteroidradar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.await
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AsteroidRepository(private val asteroidDao:AsteroidDao) {

    val readAllAsteroids: LiveData<List<Asteroid>>
        get() = asteroidDao.readAllAsteroids()

    fun getAsteroid(asteroidId: Long) = asteroidDao.getAsteroid(asteroidId)

    val getPictureOfDay = asteroidDao.getPictureOfDay()

    suspend fun refreshAsteroids(){
        val calendar = Calendar.getInstance()
        val today = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault()).format(
            calendar.time
        )
        calendar.add(Calendar.DAY_OF_YEAR, DEFAULT_END_DATE_DAYS)
        val endDate = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault()).format(
            calendar.time
        )
        withContext(Dispatchers.IO){
            try {
                val result = NasaApi.retrofitService.getProperties(today,endDate,API_KEY)
                asteroidDao.saveAllAsteroids(*parseAsteroidsJsonResult(JSONObject(result.string())).toTypedArray())
                Log.i("test","Success loading Asteroids")
            }
            catch (e : Exception){
                Log.i("test", "Failed to load Asteroids: $e")
            }
        }
    }

    fun deleteOldAsteroids(){
        val calendar = Calendar.getInstance()
        val today = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault()).format(
            calendar.time
        )
        asteroidDao.deleteOldAsteroids(today)
    }

    suspend fun refreshPicOfDay(){
        withContext(Dispatchers.IO){
            try{
                val result = NasaApi.retrofitService.getImageOfDay(API_KEY)
                asteroidDao.savePictureOfDay(result)
                Log.i("test","Success loading Picture of the Day")
            }
            catch (e: Exception){
                Log.i("test","Failed to load Picture of the Day: $e")
            }
        }
    }

    fun deleteOldPictureOfDay(){
        val calendar = Calendar.getInstance()
        val today = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault()).format(
            calendar.time
        )
        asteroidDao.deleteOldPictureOfDay(today)
    }
}