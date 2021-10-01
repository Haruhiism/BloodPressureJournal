package com.pinkmoon.bloodpressurejournal

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

const val DAY_IN_MILLIS = 86400000

const val DAY_TIME_START = "00:00:00"
const val DAY_TIME_END = "23:59:59"

object BPDate {

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
        val today = getDateToday().split("-")
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