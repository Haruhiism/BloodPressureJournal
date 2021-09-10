package com.pinkmoon.bloodpressurejournal.db.bp_reading

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class BPReadingRepository(private val bpReadingDao: BPReadingDao) {

    lateinit var startDate: String
    lateinit var endDate: String

    val allReadings: Flow<List<BPReading>> = bpReadingDao.getAllReadings()
    fun readingsByDateRange(s: String, e: String): Flow<List<BPReading>> =
        bpReadingDao.getReadingsByDateRange(e, s)
    val lastFiveReadings: Flow<List<BPReading>> = bpReadingDao.getLastFiveReadings()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(bpReading: BPReading) {
        bpReadingDao.insertBPReading(bpReading)
    }
}