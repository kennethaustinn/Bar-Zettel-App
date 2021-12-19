package de.htw_berlin.barzettel

import androidx.room.ColumnInfo
import com.x5.util.DataCapsule
import java.util.*

data class SalesOfDay(
    @ColumnInfo(name = "date") val date: Calendar,
    @ColumnInfo(name = "sum") val amount: Int
): DataCapsule {
    override fun getExports(): Array<String> {
        return arrayOf("getDateString", "getPrice")
    }

    override fun getExportPrefix(): String {
        return "item"
    }

    fun getDateString() : String{
        if (date.get(Calendar.DAY_OF_MONTH) < 10){
            return "0" + date.get(Calendar.DAY_OF_MONTH).toString() + ". " + date.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, Locale.GERMANY) + " " + date.get(Calendar.YEAR)
        }
        return date.get(Calendar.DAY_OF_MONTH).toString() + ". " + date.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, Locale.GERMANY) + " " + date.get(Calendar.YEAR)
    }

    fun getPrice() : String{
        val df = java.text.DecimalFormat("#0.00")
        val dprice = amount/100.0
        return df.format(dprice) + " €"
    }
}