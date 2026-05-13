package com.virasat.nammaguide.data.db

import androidx.room.*
import com.virasat.nammaguide.data.model.CheckIn
import kotlinx.coroutines.flow.Flow

@Dao
interface CheckInDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(checkIn: CheckIn)

    @Delete
    suspend fun delete(checkIn: CheckIn)

    @Query("SELECT * FROM checkins ORDER BY checkinTimestamp DESC")
    fun getAllCheckIns(): Flow<List<CheckIn>>

    @Query("SELECT EXISTS(SELECT 1 FROM checkins WHERE siteId = :siteId)")
    fun isCheckedIn(siteId: String): Flow<Boolean>

    @Query("SELECT COUNT(*) FROM checkins")
    fun getTotalCount(): Flow<Int>
}
