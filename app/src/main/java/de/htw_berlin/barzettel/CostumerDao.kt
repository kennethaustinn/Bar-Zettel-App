package de.htw_berlin.barzettel

import androidx.room.*
import de.dalmagrov.barzettel.Costumer
import kotlinx.coroutines.flow.Flow

@Dao
interface CostumerDao {
    @Query("SELECT * FROM Costumer")
    fun getAll() : Flow<List<Costumer>>

    @Query("SELECT * FROM Costumer WHERE date = :date")
    fun getAllToday(date : String) : Flow<List<Costumer>>

    @Insert
    fun insertAll(vararg costumer: Costumer)

    @Delete
    fun delete(costumer: Costumer)

    @Update
    fun update(costumer : Costumer)
}