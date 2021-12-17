package de.dalmagrov.barzettel

import androidx.room.*
import java.util.*


@Entity
data class Kunde(var name : String){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
    var price : Int = 0
    var date: Calendar
    var isPayed: Boolean = false
    var artikel: MutableMap<Int, Int> = mutableMapOf<Int, Int>()

    init {
        val now = Calendar.getInstance()
        if (now.get(Calendar.HOUR) < 13){
            date = Calendar.getInstance()
            date.add(Calendar.DATE, -1)
        }
        else{
            date = Calendar.getInstance()
        }
    }
}



