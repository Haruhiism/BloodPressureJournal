package com.pinkmoon.bloodpressurejournal

import android.app.Application
import com.pinkmoon.bloodpressurejournal.db.BloodPressureJournalRoomDB
import com.pinkmoon.bloodpressurejournal.db.bp_reading.BPReadingRepository
import com.pinkmoon.bloodpressurejournal.db.join_redication_reminder.JoinMedicationReminderRepository
import com.pinkmoon.bloodpressurejournal.db.medication.MedicationRepository
import com.pinkmoon.bloodpressurejournal.db.reminder.ReminderRepository

class BloodPressureJournalApplication : Application() {
    val database by lazy { BloodPressureJournalRoomDB.getDatabase(this) }
    val bpReadingRepository by lazy { BPReadingRepository(database.bpReadingDao()) }
    val medicationRepository by lazy { MedicationRepository(database.medicationDao()) }
    val reminderRepository by lazy { ReminderRepository(database.reminderDao()) }
    val joinMedicationReminderRepository by lazy { JoinMedicationReminderRepository(database.joinMedicationReminderDao()) }
}