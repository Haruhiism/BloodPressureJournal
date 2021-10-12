package com.pinkmoon.bloodpressurejournal.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pinkmoon.bloodpressurejournal.db.bp_reading.BPReading
import com.pinkmoon.bloodpressurejournal.db.bp_reading.BPReadingDao
import com.pinkmoon.bloodpressurejournal.db.join_redication_reminder.JoinMedicationReminder
import com.pinkmoon.bloodpressurejournal.db.join_redication_reminder.JoinMedicationReminderDao
import com.pinkmoon.bloodpressurejournal.db.medication.Medication
import com.pinkmoon.bloodpressurejournal.db.medication.MedicationDao
import com.pinkmoon.bloodpressurejournal.db.reminder.Reminder
import com.pinkmoon.bloodpressurejournal.db.reminder.ReminderDao

@Database(entities = arrayOf(BPReading::class,
                            Medication::class,
                            Reminder::class,
                            JoinMedicationReminder::class),
    version = 1)
public abstract class BloodPressureJournalRoomDB : RoomDatabase() {

    // Dao List
    abstract fun bpReadingDao():                BPReadingDao
    abstract fun medicationDao():               MedicationDao
    abstract fun reminderDao():                 ReminderDao
    abstract fun joinMedicationReminderDao():   JoinMedicationReminderDao

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