package de.htw_berlin.barzettel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CostumerOverviewViewModelFactory(private val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CostumerOverviewViewModel::class.java)) {
            return CostumerOverviewViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
    }