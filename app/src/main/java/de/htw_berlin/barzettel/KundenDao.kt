package de.htw_berlin.barzettel

import androidx.room.*
import de.dalmagrov.barzettel.Kunde
import kotlinx.coroutines.flow.Flow

@Dao
interface KundenDao {
    @Query("SELECT * FROM Kunde")
    fun getAll() : Flow<List<Kunde>>

    @Query("SELECT * FROM Kunde WHERE date = :date")
    fun getAllToday(date : String) : Flow<List<Kunde>>

    @Insert
    fun insertAll(vararg kunde: Kunde)

    @Delete
    fun delete(kunde: Kunde)

    @Update
    fun update(kunde : Kunde)
}