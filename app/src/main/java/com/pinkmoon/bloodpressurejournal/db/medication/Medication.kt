package com.pinkmoon.bloodpressurejournal.db.medication

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "table_medication")
data class Medication(
    var name: String,
    var dosage: Double,
    var quantity: Int,
    @PrimaryKey(autoGenerate = true) var pId: Int = 0
) : Parcelable {
}