package com.pinkmoon.bloodpressurejournal.db.medication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_medication")
data class Medication(
    var name: String,
    var dosage: Double,
    var quantity: Int,
    @PrimaryKey(autoGenerate = true) var pId: Int = 0
) {
}