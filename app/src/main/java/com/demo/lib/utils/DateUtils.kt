package com.demo.lib.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun getDate(milliSeconds: Long, dateFormat: String = "dd-MMM-yyyy"): String? {

        val formatter = SimpleDateFormat(dateFormat, Locale.US)

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }
}