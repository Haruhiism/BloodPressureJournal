package com.pinkmoon.bloodpressurejournal.db.join_redication_reminder

import kotlinx.coroutines.flow.Flow

class JoinMedicationReminderRepository(private val joinMedicationReminderDao: JoinMedicationReminderDao) {

    val allMedicationReminders: Flow<List<JoinMedicationReminder>> =
        joinMedicationReminderDao.getAllMedicationReminders()
}