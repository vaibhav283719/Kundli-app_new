package com.kundliapp.utils

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {

    fun formatDate(year: Int, month: Int, day: Int): String {
        val cal = Calendar.getInstance()
        cal.set(year, month - 1, day)
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return sdf.format(cal.time)
    }

    fun formatTime(hour: Int, minute: Int): String {
        return String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
    }

    fun getCurrentYear() = Calendar.getInstance().get(Calendar.YEAR)
    fun getCurrentMonth() = Calendar.getInstance().get(Calendar.MONTH) + 1
    fun getCurrentDay() = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    fun getCurrentHour() = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    fun getCurrentMinute() = Calendar.getInstance().get(Calendar.MINUTE)

    fun timeToDecimalHour(hour: Int, minute: Int): Double {
        return hour + minute / 60.0
    }

    fun getDayOfWeek(year: Int, month: Int, day: Int): String {
        val cal = Calendar.getInstance()
        cal.set(year, month - 1, day)
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(cal.time)
    }
}
