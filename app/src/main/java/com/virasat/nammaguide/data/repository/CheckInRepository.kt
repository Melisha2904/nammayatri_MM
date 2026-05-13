package com.virasat.nammaguide.data.repository

import com.virasat.nammaguide.data.db.CheckInDao
import com.virasat.nammaguide.data.model.CheckIn
import kotlinx.coroutines.flow.Flow

class CheckInRepository(private val dao: CheckInDao) {
    val allCheckIns: Flow<List<CheckIn>> = dao.getAllCheckIns()
    val totalCount: Flow<Int> = dao.getTotalCount()

    suspend fun checkIn(checkIn: CheckIn) = dao.insert(checkIn)
    suspend fun checkOut(checkIn: CheckIn) = dao.delete(checkIn)
    fun isCheckedIn(siteId: String): Flow<Boolean> = dao.isCheckedIn(siteId)
}
