package com.pinkmoon.bloodpressurejournal.db.bp_reading

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BPReadingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBPReading(bpReading: BPReading)

    @Query("SELECT * FROM table_bp_reading ORDER BY timeStamp DESC")
    fun getAllReadings(): Flow<List<BPReading>>
}