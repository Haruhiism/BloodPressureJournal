package com.pinkmoon.bloodpressurejournal.db.join_redication_reminder

import android.os.Parcelable
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.pinkmoon.bloodpressurejournal.db.medication.Medication
import com.pinkmoon.bloodpressurejournal.db.reminder.Reminder
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "table_join_medication_reminder",
        foreignKeys = arrayOf(
            ForeignKey(entity = Medication::class,
                        parentColumns = arrayOf("pId"),
                        childColumns = arrayOf("medId")),
            ForeignKey(entity = Reminder::class,
                        parentColumns = arrayOf("pId"),
                        childColumns = arrayOf("remId"))
        ))
data class JoinMedicationReminder(
    val medId: Int,
    val name: String,
    val dosage: Double,
    val quantity: Int,
    val remId: Int,
    @Nullable val scheduledTime: String?,
    @PrimaryKey(autoGenerate = true) var pId: Int = 0
) : Parcelable {
}