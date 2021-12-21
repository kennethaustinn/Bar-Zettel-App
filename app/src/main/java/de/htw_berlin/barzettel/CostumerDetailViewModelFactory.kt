package de.htw_berlin.barzettel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CostumerDetailViewModelFactory(private val context: Context, private val costumerId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CostumerDetailViewModel::class.java)) {
            return CostumerDetailViewModel(context, costumerId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}