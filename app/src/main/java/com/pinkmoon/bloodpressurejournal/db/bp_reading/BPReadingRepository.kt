package com.pinkmoon.bloodpressurejournal.db.bp_reading

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class BPReadingRepository(private val bpReadingDao: BPReadingDao) {

    val allReadings: Flow<List<BPReading>> = bpReadingDao.getAllReadings()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(bpReading: BPReading) {
        bpReadingDao.insertBPReading(bpReading)
    }
}