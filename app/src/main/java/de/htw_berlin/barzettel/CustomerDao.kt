package de.htw_berlin.barzettel

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Query("SELECT * FROM Customer")
    fun getAll() : Flow<List<Customer>>

    @Query("SELECT * FROM Customer WHERE date = :date")
    fun getAllToday(date : String) : Flow<List<Customer>>

    @Query("SELECT * FROM Customer WHERE id = :id")
    fun getCostumer(id: Int) : Customer

    @Query("SELECT date, SUM(price) as sum FROM Customer WHERE date LIKE '%' || :datum || '%' GROUP BY date")
    fun getMonth(datum : String) : List<SalesOfDay>

    @Insert
    fun insertAll(vararg customer: Customer)

    @Delete
    fun delete(customer: Customer)

    @Update
    fun update(customer : Customer)
}