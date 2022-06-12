package com.example.utils.extensions

import com.example.utils.DateUtils
import java.text.SimpleDateFormat
import java.util.*


fun String.toDate(pattern: String = DateUtils.DEFAULT_DATE_PATTERN): Date? {
    val formatter = SimpleDateFormat(pattern, Locale.US)
    return formatter.parse(this)
}
