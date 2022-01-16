package de.htw_berlin.barzettel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CustomerDetailViewModelFactory(private val application: Application, private val costumerId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerDetailViewModel::class.java)) {
            return CustomerDetailViewModel(application, costumerId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}