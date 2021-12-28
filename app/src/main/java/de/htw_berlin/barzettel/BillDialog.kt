package de.htw_berlin.barzettel

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import de.htw_berlin.barzettel.databinding.DialogBillBinding

class BillDialog(val viewModel: CostumerDetailViewModel) : DialogFragment() {

    var binding: DialogBillBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater;
        val view = inflater.inflate(R.layout.dialog_bill, null)
        binding = DialogBillBinding.bind(view)
        binding?.viewModel = viewModel
        return AlertDialog.Builder(context)
            .setView(binding?.root)
            .create()
    }
}