package de.htw_berlin.barzettel

import androidx.room.*
import java.util.*


@Entity
data class Customer(var name : String){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
    var price : Int = 0
    var date: Calendar
    var isPayed: Boolean = false
    var article: MutableMap<Int, Int> = mutableMapOf()

    init {
        val now = Calendar.getInstance()
        if (now.get(Calendar.HOUR_OF_DAY) < 13){
            date = Calendar.getInstance()
            date.add(Calendar.DAY_OF_MONTH, -1)
        }
        else{
            date = Calendar.getInstance()
        }
    }
}



