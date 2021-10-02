package com.pinkmoon.bloodpressurejournal.db.reminder

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Insert
    suspend fun insertReminder(reminder: Reminder)

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)

    @Query("SELECT * FROM table_reminder ORDER BY startDate DESC")
    fun getAllReminders(): Flow<List<Reminder>>
}