package com.examples.weatherforecast.utils

import java.text.SimpleDateFormat
import java.util.*


fun formatDate(timestamp: Int): String {
    return SimpleDateFormat("EEE, MMM d").format(Date(timestamp.toLong()*1000))
}