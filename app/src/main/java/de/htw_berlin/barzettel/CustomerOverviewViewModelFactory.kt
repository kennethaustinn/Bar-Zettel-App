package de.htw_berlin.barzettel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CustomerOverviewViewModelFactory(private val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerOverviewViewModel::class.java)) {
            return CustomerOverviewViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
    }