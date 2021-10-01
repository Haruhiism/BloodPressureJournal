package com.pinkmoon.bloodpressurejournal.db.bp_reading

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat

private const val MINIMUM_AVG_COMPUTATION_SIZE = 3


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

}



