package com.examples.weatherforecast.utils

import java.text.SimpleDateFormat
import java.util.*


fun formatDate(timestamp: Int): String {
    return SimpleDateFormat("EEE, MMM d").format(Date(timestamp.toLong() * 1000))
}

fun getDayOfWeek(timestamp: Int): String {
    return SimpleDateFormat("EEE").format(Date(timestamp.toLong() *1000))
}

fun getMonthDate(timestamp: Int): String {
    return SimpleDateFormat("MMM,dd").format(Date(timestamp.toLong() *1000))
}

fun formatDecimals(value: Double): String {
    return "%.0f".format(value)
}

fun formatToHourMinutes(timestamp: Long): String {
    val sdf = SimpleDateFormat("hh:mm aa")
    return sdf.format(Date(timestamp * 1000))
}

fun getAmOrPm(amOrPm: Int): String {
    return if (amOrPm == 0)
        "AM"
    else
        "PM"
}
