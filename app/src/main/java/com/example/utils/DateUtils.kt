package com.example.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    const val DEFAULT_DATE_PATTERN = "yyyy-MM-dd"

    fun getYesterdayDate(): Date {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        return cal.time;
    }

    fun getTodayDate(): Date {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, 0)
        return cal.time;
    }

    fun getTomorrowDate(): Date {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, 1)
        return cal.time;
    }

    fun getCurrentYear(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.YEAR)
    }
}

fun Date.asString(pattern: String = DateUtils.DEFAULT_DATE_PATTERN): String {
    val formatter = SimpleDateFormat(pattern, Locale.US)
    return formatter.format(this.time)
}

