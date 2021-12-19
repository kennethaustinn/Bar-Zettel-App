package de.htw_berlin.barzettel

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

class ConverterDatabase {

    private val sdf: SimpleDateFormat = SimpleDateFormat("MM/dd/yyyy")

    @TypeConverter
    fun fromDateToString(value : Calendar?) : String?{
        return sdf.format(value?.time)
    }

    @TypeConverter
    fun fromStringToDate(value : String?) : Calendar?{
        val cal = Calendar.getInstance()
        cal.time = sdf.parse(value)
        return cal
    }

    @TypeConverter
    fun fromListPairToString(value : MutableMap<Int, Int>?) : String?{
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToListPair(value : String?) : MutableMap<Int, Int>?{
        return Gson().fromJson(value, object : TypeToken<MutableMap<Int, Int>>() {}.type)
    }
}