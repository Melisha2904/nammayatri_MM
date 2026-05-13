package com.virasat.nammaguide.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.virasat.nammaguide.data.model.CheckIn
import com.virasat.nammaguide.data.model.Favorite

@Database(entities = [CheckIn::class, Favorite::class], version = 2, exportSchema = false)
abstract class VirastatDatabase : RoomDatabase() {

    abstract fun checkInDao(): CheckInDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: VirastatDatabase? = null

        fun getDatabase(context: Context): VirastatDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VirastatDatabase::class.java,
                    "virasat_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
