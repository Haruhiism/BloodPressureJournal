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

    //SELECT * FROM table_bp_reading WHERE timeStamp BETWEEN :startDate + '%' AND :endDate + '%'
    @Query("SELECT * FROM table_bp_reading WHERE DATE(timeStamp) BETWEEN :startDate AND :endDate")
    fun getReadingsByDateRange(startDate: String, endDate: String): Flow<List<BPReading>>

    @Query("SELECT * FROM table_bp_reading ORDER BY timeStamp DESC LIMIT 5")
    fun getLastFiveReadings(): Flow<List<BPReading>>
}