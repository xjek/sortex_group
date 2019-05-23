package com.eugene.softexgroup.utils

import java.text.SimpleDateFormat
import java.util.*

const val SERVER_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSSS"

const val APP_DATE_FORMAT = "yyyy-MM-dd HH:mm"

/**
 * Парсинг даты
 */
fun parseDate(dateString: String?, format: String): Date? {
    var date: Date? = null
    if (dateString != null && !dateString.isEmpty()) {
        try {
            val dateFormat = SimpleDateFormat(format, Locale.getDefault())
            date = dateFormat.parse(dateString)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    return date
}

/**
 * Форматирование даты
 */
fun formatDate(date: Date?, format: String): String {
    var dateString = ""
    if (date != null && date.time != 0L) {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        dateString = dateFormat.format(date)
    }
    return dateString
}