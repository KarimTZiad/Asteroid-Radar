package com.example.asteroidradar.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailsFragmentViewModelFactory(private val application: Application, private val asteroidId: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsFragmentViewModel::class.java)) {
            return DetailsFragmentViewModel(application, asteroidId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}