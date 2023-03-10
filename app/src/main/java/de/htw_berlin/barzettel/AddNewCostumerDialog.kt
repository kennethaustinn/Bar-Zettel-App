package de.htw_berlin.barzettel

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import de.htw_berlin.barzettel.databinding.DialogAddNewCostumerBinding

class AddNewCostumerDialog(val onOK: (String) -> (Unit)) : DialogFragment() {

    var binding: DialogAddNewCostumerBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_add_new_costumer, null)
        binding = DialogAddNewCostumerBinding.bind(view)
        binding?.buttonOKDialogNewCostumer?.setOnClickListener {
            Log.d(CustomerOverviewViewModel.TAG, "Dialog OK clicked")
            onOK(binding?.dialogNameCostumerNew?.text.toString())
            this.dialog?.cancel()
        }
        binding?.buttonCancelDialogNewCoustumer?.setOnClickListener {
            Log.d(CustomerOverviewViewModel.TAG, "Dialog Cancel clicked")
            this.dialog?.cancel()
        }
        return AlertDialog.Builder(context)
            .setView(binding?.root)
            .create()
    }
}