package de.htw_berlin.barzettel

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.findFragment
import androidx.lifecycle.*
import androidx.navigation.findNavController
import de.dalmagrov.barzettel.Kunde
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CostumerOverviewViewModel(private val context: Context) : ViewModel() {

    val repository: KundenRepository
    val isDialogVisible = MutableLiveData<Boolean>()
    val kundenToday : LiveData<List<Kunde>>

    init {
        repository = KundenRepository(context)
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
        val kunde = Kunde(description)
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(kunde)
        }
    }

}