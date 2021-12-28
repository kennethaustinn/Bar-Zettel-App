package de.htw_berlin.barzettel

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface CostumerDao {
    @Query("SELECT * FROM Costumer")
    fun getAll() : Flow<List<Costumer>>

    @Query("SELECT * FROM Costumer WHERE date = :date")
    fun getAllToday(date : String) : Flow<List<Costumer>>

    @Query("SELECT * FROM Costumer WHERE id = :id")
    fun getCostumer(id: Int) : Costumer

    @Query("SELECT date, SUM(price) as sum FROM Costumer WHERE date LIKE '%' || :datum || '%' GROUP BY date")
    fun getMonth(datum : String) : List<SalesOfDay>

    @Insert
    fun insertAll(vararg costumer: Costumer)

    @Delete
    fun delete(costumer: Costumer)

    @Update
    fun update(costumer : Costumer)
}