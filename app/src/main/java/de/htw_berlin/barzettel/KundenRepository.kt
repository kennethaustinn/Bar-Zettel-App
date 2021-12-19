package de.htw_berlin.barzettel

import android.content.Context
import de.dalmagrov.barzettel.Kunde
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

class KundenRepository(val context: Context) {
    val dao: KundenDao
    private val sdf: SimpleDateFormat = SimpleDateFormat("MM/dd/yyyy")
    val allKundenToday : Flow<List<Kunde>>

    init {
        val db = AppDatabase.getDatabase(context)
        dao = db.kundenDao()
        allKundenToday = dao.getAllToday(getDate())
    }

    suspend fun insert(kunde: Kunde) {
        dao.insertAll(kunde)
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