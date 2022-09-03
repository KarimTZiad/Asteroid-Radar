package com.example.asteroidradar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.asteroidradar.data.AsteroidDatabase.Companion.getDatabase
import com.example.asteroidradar.data.AsteroidRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object{
        const val WORK_NAME = "RefreshAsteroidAndPictureOfDayWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = AsteroidRepository(database.asteroidDao())
        return try {
            repository.refreshAsteroids()
            repository.refreshPicOfDay()
            repository.deleteOldAsteroids()
            repository.deleteOldPictureOfDay()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }

    }
}