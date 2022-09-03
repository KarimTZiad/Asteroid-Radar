package com.example.asteroidradar.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.asteroidradar.Asteroid
import com.example.asteroidradar.data.AsteroidDatabase
import com.example.asteroidradar.data.AsteroidRepository
import kotlinx.coroutines.launch

class DetailsFragmentViewModel(application: Application, asteroidId: Long) : AndroidViewModel(application) {

    private val repository : AsteroidRepository
    val asteroid: LiveData<Asteroid>

    init {
        val dao = AsteroidDatabase.getDatabase(application).asteroidDao()
        repository = AsteroidRepository(dao)
        asteroid = repository.getAsteroid(asteroidId)
    }
}