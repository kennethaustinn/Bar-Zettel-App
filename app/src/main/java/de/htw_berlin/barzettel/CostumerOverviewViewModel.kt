package de.htw_berlin.barzettel

import android.app.Dialog
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.findFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController

class CostumerOverviewViewModel : ViewModel() {

    val isDialogVisible = MutableLiveData<Boolean>()

    companion object{
        val TAG = "CostumerOverview"
    }

    fun onFabClick(view: View) {
        Log.d(TAG, "Costumer add clicked")
        isDialogVisible.value = true
    }

    fun onOkDialogClicked(){

    }

}