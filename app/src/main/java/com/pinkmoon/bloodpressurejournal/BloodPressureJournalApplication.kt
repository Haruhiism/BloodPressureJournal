package com.pinkmoon.bloodpressurejournal

import android.app.Application
import com.pinkmoon.bloodpressurejournal.db.BloodPressureJournalRoomDB
import com.pinkmoon.bloodpressurejournal.db.bp_reading.BPReadingRepository

class BloodPressureJournalApplication : Application() {
    val database by lazy { BloodPressureJournalRoomDB.getDatabase(this) }
    val bpReadingRepository by lazy { BPReadingRepository(database.bpReadingDao()) }
}