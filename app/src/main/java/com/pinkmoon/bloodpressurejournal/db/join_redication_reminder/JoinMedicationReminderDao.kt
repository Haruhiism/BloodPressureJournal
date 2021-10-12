package com.pinkmoon.bloodpressurejournal.db.join_redication_reminder

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JoinMedicationReminderDao {

    @Insert
    suspend fun insertJoinMedicationReminder(joinMedicationReminder: JoinMedicationReminder)

    @Query("SELECT table_medication.pId AS medId, table_medication.name, table_medication.dosage, table_medication.quantity, table_reminder.pId AS remId, table_reminder.scheduledTime, table_medication.pId as pId FROM table_medication LEFT JOIN table_reminder ON table_medication.pId = table_reminder.medId")
    fun getAllMedicationReminders(): Flow<List<JoinMedicationReminder>>
}