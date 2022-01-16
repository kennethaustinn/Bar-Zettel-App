package de.htw_berlin.barzettel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CustomerOverviewViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerOverviewViewModel::class.java)) {
            return CustomerOverviewViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
    }