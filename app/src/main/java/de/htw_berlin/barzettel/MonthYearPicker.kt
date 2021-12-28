package de.htw_berlin.barzettel

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class MonthYearPicker(private val selectedDate: Calendar, private val listener: DatePickerDialog.OnDateSetListener) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = DatePickerDialog(this.requireContext(), listener, selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH))
        return dialog
    }
}