package de.htw_berlin.barzettel

import android.content.Context
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

class CustomerRepository(val context: Context) {
    val dao: CustomerDao
    private val sdf: SimpleDateFormat = SimpleDateFormat("MM/dd/yyyy")
    val allKundenToday : Flow<List<Customer>>

    init {
        val db = AppDatabase.getDatabase(context)
        dao = db.kundenDao()
        allKundenToday = dao.getAllToday(getDate())
    }

    suspend fun insert(customer: Customer) {
        dao.insertAll(customer)
    }

    suspend fun getCostumer(id: Int) : Customer{
        return dao.getCostumer(id)
    }

    suspend fun getSalesOfMonth(date: Calendar) : List<SalesOfDay>{
        val month = date.get(Calendar.MONTH) + 1
        var res = dao.getMonth(month.toString())
        res = res.filter {
            it.date.get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
                    it.date.get(Calendar.YEAR) == date.get(Calendar.YEAR)
        }
        res = res.sortedBy { it.date }
        return res
    }

    suspend fun updateCostumer(customer: Customer){
        dao.update(customer)
    }

    private fun getDate() : String{
        var now = Calendar.getInstance()
        if (now.get(Calendar.HOUR_OF_DAY) < 13){
            val cal = Calendar.getInstance()
            cal.add(Calendar.DAY_OF_MONTH, -1)
            return sdf.format(cal.time)
        }else{
            return sdf.format(Calendar.getInstance().time)
        }
    }
}