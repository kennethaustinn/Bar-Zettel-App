package de.htw_berlin.barzettel

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import de.dalmagrov.barzettel.Costumer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CostumerOverviewViewModel(private val context: Context) : ViewModel() {

    val repository: CostumerRepository
    val isDialogVisible = MutableLiveData<Boolean>()
    val kundenToday : LiveData<List<Costumer>>

    init {
        repository = CostumerRepository(context)
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
        val kunde = Costumer(description)
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(kunde)
        }
    }

    fun onListItemClicked(position : Int){

    }

    fun onListItemLongClicked(position : Int) {

    }

}