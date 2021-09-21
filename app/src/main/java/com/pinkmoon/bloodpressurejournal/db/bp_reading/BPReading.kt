package com.pinkmoon.bloodpressurejournal.db.bp_reading

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pinkmoon.bloodpressurejournal.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val MINIMUM_AVG_COMPUTATION_SIZE = 3
const val DAY_IN_MILLIS = 86400000

const val DAY_TIME_START = "00:00:00"
const val DAY_TIME_END = "23:59:59"

@Entity(tableName = "table_bp_reading")
class BPReading(
    var systolicValue: Int = 120,
    var diastolicValue: Int = 80,
    var pulseValue: Int = 72,
    var timeStamp: String = getDateTimeStamp(), // DateFormat.getDateTimeInstance().format(System.currentTimeMillis())
    @PrimaryKey(autoGenerate = true) var pId: Int = 0
) {

    companion object {
        private fun getDateTimeStamp(): String {
            var df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return df.format(System.currentTimeMillis())
        }
    }

    /**
     * Calculate the average of 3 or more consecutive readings.
     * Consecutive readings are denoted by having a matching time stamp.
     * @param bpReadings n number of consecutive readings
     * @return a BPReading object containing the averages as its
     *  properties, with a timestamp matching the consecutive readings.
     */
    private fun calculateAverage(vararg bpReadings: BPReading) : BPReading {
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
            bpReadings[0].timeStamp)
    }

    /**
     * Takes in a list of readings, filters them by a matching time stamp,
     * calls on a helper function to calculate the average.
     * @param bpReadings a list of readings that could contain matching timestamps.
     * @return a list of BPReading objects that contain average values.
     */
    fun filterAverages(bpReadings: List<BPReading>): List<BPReading> {
        var bpReadingAverageList = mutableListOf<BPReading>()
        var batchHolder = mutableListOf<BPReading>()
        var bpReadingIds = mutableListOf<Int>()
        for (bp in bpReadings) {
            val currentTimestamp = bp.timeStamp
            if(bp.pId !in bpReadingIds) {
                for (inner in bpReadings) {
                    if(currentTimestamp == inner.timeStamp){
                        batchHolder.add(inner)
                        bpReadingIds.add(inner.pId)
                    }
                }
                if(batchHolder.size >= MINIMUM_AVG_COMPUTATION_SIZE) {
                    bpReadingAverageList.add(calculateAverage(*batchHolder.toTypedArray()))
                }
                batchHolder.clear()
            }
        }
        return bpReadingAverageList
    }

    /**
     * Format a date that is pulled from the database in the form
     * of yyyy-MM-dd HH:mm:ss into a "pretty" format.
     * @param dateParam the standard date form of the timestamp from the database
     * @return a pretty format of the standard date, ex: Thursday, October 15 09:00:00
     */
    fun formatDatePretty(dateParam: String): String {
        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val aLDT = LocalDateTime.parse(dateParam, dtf)

        val prettyF = DateTimeFormatter.ofPattern("EEEE MMM d, yyyy HH:mm:ss")
        return prettyF.format(aLDT)
    }
}

// vvv Top level functions vvv
// TODO: As these functions have grown in number and other things, it has become
//  apparent that they belong in their own class. Ensure to make a separate
//  custom Date (maybe abstract) class to keep these functions and call them accordingly.

/**
 * Will calculate the range of the week today's date falls upon.
 * For example, if today is Wednesday 15th of September 2021,
 * the week range would be from Monday 2021-09-13 to Sunday 2021-09-19.
 * @return a list with the first and second items within it being string
 * representations of the start and end dates of the current week.
 */
fun getWeekRange(): MutableList<String> {
    val weekRange = mutableListOf<String>()

    val dayNumOfWeek: DateFormat = SimpleDateFormat("u")
    val currentDayNumOfMonth: DateFormat = SimpleDateFormat("d")
    // knowing that Monday = 1 and Sunday = 7
    val daysPastMonday = dayNumOfWeek.format(Calendar.getInstance().time).toInt() - 1
    val daysUntilSunday = 7 - dayNumOfWeek.format(Calendar.getInstance().time).toInt()

    // calculate the day of the month the week starts and ends in
    val weekStartDayOfMonth = currentDayNumOfMonth
        .format(Calendar.getInstance().time).toInt() - daysPastMonday
    val weekEndDayOfMonth = currentDayNumOfMonth
        .format(Calendar.getInstance().time).toInt() + daysUntilSunday

    // get a string instance of the date, split and manipulate the string
    // according to the calculated start and end dates above
    val today = getDateEndToday().split("-")
    val weekStartDate = today[0] + "-" + today[1] + "-" + weekStartDayOfMonth + " " + DAY_TIME_START
    val weekEndDate = today[0] + "-" + today[1] + "-" + weekEndDayOfMonth + " " + DAY_TIME_END

    weekRange.add(weekStartDate)
    weekRange.add(weekEndDate)

    return weekRange
}

fun getDateToday(): String {
    val df = SimpleDateFormat("yyyy-MM-dd")
    return df.format(Calendar.getInstance().time)
}

fun getDateTomorrow(): String {
    val df = SimpleDateFormat("yyyy-MM-dd")
    return df.format(System.currentTimeMillis() + DAY_IN_MILLIS)
}

fun getDateEndToday(): String {
    val df = SimpleDateFormat("yyyy-MM-dd")
    return df.format(System.currentTimeMillis())
}



