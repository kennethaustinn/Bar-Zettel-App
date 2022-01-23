package de.htw_berlin.barzettel

import androidx.room.ColumnInfo
import com.x5.util.DataCapsule
import java.text.SimpleDateFormat
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

    fun getDateString() : String {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val a = sdf.format(date.time)
        return a
    }

    fun getPrice() : String{
        val df = java.text.DecimalFormat("#0.00")
        val dprice = amount/100.0
        return df.format(dprice) + " €"
    }
}