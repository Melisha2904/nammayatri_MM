package com.virasat.nammaguide.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room Entity representing a user's check-in at a heritage site.
 * Persisted in local database to form the "Travel Passport".
 */
@Entity(tableName = "checkins")
data class CheckIn(
    @PrimaryKey
    val siteId: String,
    val siteName: String,
    val location: String,
    val checkinTimestamp: Long = System.currentTimeMillis(),
    val notes: String = ""
)
