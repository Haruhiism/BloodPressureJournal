package com.pinkmoon.bloodpressurejournal.db.bp_reading

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "table_bp_reading")
class BPReading(
    var systolicValue: Int = 120,
    var diastolicValue: Int = 80,
    var pulseValue: Int = 72,
    var timeStamp: String = getDateTimeStamp(), // DateFormat.getDateTimeInstance().format(System.currentTimeMillis())
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

    fun formatDatePretty(dateParam: String): String {
        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val aLDT = LocalDateTime.parse(dateParam, dtf)

        val prettyF = DateTimeFormatter.ofPattern("EEEE MMM d, yyyy HH:mm:ss")
        return prettyF.format(aLDT)
    }
}

private fun getDateTimeStamp(): String {
    var df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return df.format(System.currentTimeMillis())
}

val getDateStartToday: String by lazy {
    val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    df.format(System.currentTimeMillis())
}

val getDateEndToday: String by lazy {
    val df = SimpleDateFormat("yyyy-MM-dd")
    df.format(System.currentTimeMillis())
}


