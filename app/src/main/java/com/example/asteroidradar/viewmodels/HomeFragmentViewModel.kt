package com.example.asteroidradar.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.asteroidradar.Asteroid
import com.example.asteroidradar.PictureOfDay
import com.example.asteroidradar.data.AsteroidDatabase
import com.example.asteroidradar.data.AsteroidRepository
import kotlinx.coroutines.launch

class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : AsteroidRepository
    val readAllAsteroids: LiveData<List<Asteroid>>
    val pictureOfDay: LiveData<PictureOfDay>

    private val _currentlySelectedAsteroids = MutableLiveData<List<Asteroid>>()
    val currentlySelectedAsteroids: LiveData<List<Asteroid>>
    get() = _currentlySelectedAsteroids

    init {
        val dao = AsteroidDatabase.getDatabase(application).asteroidDao()
        repository = AsteroidRepository(dao)
        viewModelScope.launch {
            repository.refreshAsteroids()
            repository.refreshPicOfDay()
        }
        readAllAsteroids = repository.readAllAsteroids
        pictureOfDay = repository.getPictureOfDay
        readAllAsteroids.observeOnce(Observer {
            _currentlySelectedAsteroids.value = it
        })
    }

    fun getWeekAsteroids(){
        repository.getWeekAsteroids().observeOnce(Observer {
            _currentlySelectedAsteroids.value = it
        })
    }

    fun getTodayAsteroids(){
        repository.getTodayAsteroids().observeOnce(Observer {
            _currentlySelectedAsteroids.value = it
        })
    }

    fun getAllAsteroids(){
        repository.readAllAsteroids.observeOnce(Observer {
            _currentlySelectedAsteroids.value = it
        })
    }

    private fun <T> LiveData<T>.observeOnce(observer: Observer<T>) {
        observeForever(object : Observer<T> {
            override fun onChanged(t: T?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }
}