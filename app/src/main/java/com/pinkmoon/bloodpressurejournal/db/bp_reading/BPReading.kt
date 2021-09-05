package com.pinkmoon.bloodpressurejournal.db.bp_reading

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity(tableName = "table_bp_reading")
class BPReading(
    var systolicValue: Int,
    var diastolicValue: Int,
    var pulseValue: Int,
    var timeStamp: String = DateFormat.getDateTimeInstance().format(System.currentTimeMillis()),
    @PrimaryKey(autoGenerate = true) var pId: Int = 0
) {
    fun calculateAverage(vararg bpReadings: BPReading) : BPReading {
        var systolicAvg = 0
        var diastolicAvg = 0
        var pulseAvg = 0

        for (bpReading in bpReadings) {
            systolicAvg += bpReading.systolicValue
            diastolicAvg += bpReading.diastolicValue
            pulseAvg += bpReading.pulseValue
        }

        systolicAvg /= bpReadings.size
        diastolicAvg /= bpReadings.size
        pulseAvg /= bpReadings.size

        return BPReading(
            systolicAvg,
            diastolicAvg,
            pulseAvg,
            DateFormat.getDateTimeInstance().format(System.currentTimeMillis()))
    }
}