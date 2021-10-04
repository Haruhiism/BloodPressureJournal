package com.pinkmoon.bloodpressurejournal.db.reminder

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class ReminderRepository(private val reminderDao: ReminderDao) {

    val allReminders: Flow<List<Reminder>> = reminderDao.getAllReminders()

    @WorkerThread
    suspend fun insertReminder(reminder: Reminder) {
        reminderDao.insertReminder(reminder)
    }

    @WorkerThread
    suspend fun updateReminder(reminder: Reminder){
        reminderDao.updateReminder(reminder)
    }

    @WorkerThread
    suspend fun deleteReminder(reminder: Reminder){
        reminderDao.deleteReminder(reminder)
    }
}