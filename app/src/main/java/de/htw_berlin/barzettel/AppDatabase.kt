package de.htw_berlin.barzettel

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@Database(entities = [Customer::class], version = 1)
@TypeConverters(ConverterDatabase::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "CustomerDB"
                )
                    .addCallback(CustomerDatabaseCallback())
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
    private class CustomerDatabaseCallback : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                MainScope().launch {
                    val dao = database.customerDao()
                    dao.getAll()
                }
            }
        }

    }
}