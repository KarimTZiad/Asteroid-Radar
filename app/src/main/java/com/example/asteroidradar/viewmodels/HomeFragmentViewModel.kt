package com.example.asteroidradar.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.asteroidradar.Asteroid
import com.example.asteroidradar.PictureOfDay
import com.example.asteroidradar.data.AsteroidDatabase
import com.example.asteroidradar.data.AsteroidRepository
import kotlinx.coroutines.launch

class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : AsteroidRepository
    val readAllAsteroids: LiveData<List<Asteroid>>
    val pictureOfDay: LiveData<PictureOfDay>

    init {
        val dao = AsteroidDatabase.getDatabase(application).asteroidDao()
        repository = AsteroidRepository(dao)
        viewModelScope.launch {
            repository.refreshAsteroids()
            repository.refreshPicOfDay()
        }
        readAllAsteroids = repository.readAllAsteroids
        pictureOfDay = repository.getPictureOfDay
    }
}