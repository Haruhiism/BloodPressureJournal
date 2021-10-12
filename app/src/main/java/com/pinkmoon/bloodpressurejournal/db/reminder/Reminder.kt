package com.pinkmoon.bloodpressurejournal.db.reminder

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.pinkmoon.bloodpressurejournal.db.medication.Medication
import java.text.SimpleDateFormat

@Entity(tableName = "table_reminder",
        foreignKeys = arrayOf(
            ForeignKey(entity = Medication::class,
                        parentColumns = arrayOf("pId"),
                        childColumns = arrayOf("medId"),
                        onDelete = CASCADE)
        )
)
data class Reminder(
    var startDate: String = getDateTimeStamp(),
    var notificationSent: Boolean,
    var medId: Int,
    var scheduledTime: String,
    @PrimaryKey(autoGenerate = true) var pId: Int = 0
) {
    companion object {
        private fun getDateTimeStamp(): String {
            var df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return df.format(System.currentTimeMillis())
        }
    }
}