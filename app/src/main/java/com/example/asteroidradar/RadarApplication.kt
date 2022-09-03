package com.example.asteroidradar

import android.app.Application
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class RadarApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()

        applicationScope.launch {
            val constraints = Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build()

            val request = PeriodicWorkRequestBuilder<RefreshDataWorker>(
                1,
                TimeUnit.DAYS
            ).setConstraints(constraints).build()

            WorkManager.getInstance().enqueueUniquePeriodicWork(
                RefreshDataWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}