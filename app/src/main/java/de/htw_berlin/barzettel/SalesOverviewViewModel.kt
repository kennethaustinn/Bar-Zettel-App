package de.htw_berlin.barzettel

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x5.template.Theme
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class SalesOverviewViewModel(private val context: Context) : ViewModel(), DatePickerDialog.OnDateSetListener {

    val repository: CostumerRepository
    private val _date : MutableLiveData<Calendar> = MutableLiveData()
    val date: LiveData<Calendar>
    get() = _date
    private val _salesMonth: MutableLiveData<List<SalesOfDay>> = MutableLiveData()
    val salesMonth: LiveData<List<SalesOfDay>>
    get() = _salesMonth


    init {
        repository = CostumerRepository(context)
        _date.value = Calendar.getInstance()
        runBlocking {
            val deferred = async(Dispatchers.IO) { repository.getSalesOfMonth(_date.value!!) }
            _salesMonth.value = deferred.await()
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val newDate = Calendar.getInstance()
        newDate.set(Calendar.YEAR, year)
        newDate.set(Calendar.MONTH, month)
        newDate.set(Calendar.DAY_OF_MONTH, day)
        _date.value = newDate
        viewModelScope.launch {
            val deferred = async(Dispatchers.IO) { repository.getSalesOfMonth(_date.value!!) }
            _salesMonth.value = deferred.await()
        }
    }

    fun renderSalesOfDayToHTML() : String{
        _salesMonth.value?.let {
            if(it.isEmpty()){
                return ""
            }
            val theme = Theme()
            val html = theme.makeChunk()
            val file = "res/raw/bill.chtml"
            val chtml = this.javaClass.classLoader.getResourceAsStream(file).bufferedReader().readText()
            html.append(chtml)
            val items = it
            html.set("monat", it[0].date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.GERMAN))
            html.set("items", items)
            html.set("jahr", it[0].date.get(Calendar.YEAR))
            var total = 0
            it.forEach {
                total = total + it.amount
            }
            val dprice = total / 100.0
            val df = java.text.DecimalFormat("#0.00")
            html.set("total", df.format(dprice) + " €")
            return html.toString()
        }
        return ""
    }
}