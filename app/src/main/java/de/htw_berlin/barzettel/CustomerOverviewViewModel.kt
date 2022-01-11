package de.htw_berlin.barzettel

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomerOverviewViewModel(private val context: Context) : ViewModel() {

    val repository: CustomerRepository
    val isDialogVisible = MutableLiveData<Boolean>()
    val kundenToday : LiveData<List<Customer>>

    init {
        repository = CustomerRepository(context)
        kundenToday = repository.allKundenToday.asLiveData()
    }

    companion object{
        val TAG = "CostumerOverview"
    }

    fun onFabClick(view: View) {
        Log.d(TAG, "Costumer add clicked")
        isDialogVisible.value = true
    }

    fun onOkDialogClicked(description : String){
        val kunde = Customer(description)
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(kunde)
        }
    }

    fun onListItemClicked(view: View, costumerId : Int){
        val action = CustomerOverviewFragmentDirections.actionCostumerOverviewFragmentToCostumerDetailFragment(costumerId)
        isDialogVisible.value = false
        view.findNavController().navigate(action)
    }

    fun onListItemLongClicked(position : Int) {

    }

}