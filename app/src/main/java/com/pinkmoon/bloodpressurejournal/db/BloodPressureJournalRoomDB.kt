package com.pinkmoon.bloodpressurejournal.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pinkmoon.bloodpressurejournal.db.bp_reading.BPReading
import com.pinkmoon.bloodpressurejournal.db.bp_reading.BPReadingDao

@Database(entities = arrayOf(BPReading::class), version = 1)
public abstract class BloodPressureJournalRoomDB : RoomDatabase() {

    // Dao List
    abstract fun bpReadingDao(): BPReadingDao

    // Companion Object
    companion object {
        @Volatile
        private var INSTANCE: BloodPressureJournalRoomDB? = null

        fun getDatabase(context: Context): BloodPressureJournalRoomDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BloodPressureJournalRoomDB::class.java,
                    "bp_journal_db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}