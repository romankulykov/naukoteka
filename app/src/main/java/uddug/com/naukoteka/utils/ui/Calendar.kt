package uddug.com.naukoteka.utils.ui

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

const val MONTH_DATE_YEAR_HOUR_MINUTE_FORMAT = "MMM dd. yyyy HH:mm"
const val HOUR_MINUTE_FORMAT = "HH:mm"
const val MONTH_DATE_YEAR_FORMAT = "dd.MM.y"

fun Calendar?.wasOnlineTenMinutesAgo(): Boolean {
    return true
}

@SuppressLint("SimpleDateFormat")
fun Calendar.monthDateYearFormat(): String {
    return SimpleDateFormat(MONTH_DATE_YEAR_FORMAT).format(Date(this.timeInMillis))
}

@SuppressLint("SimpleDateFormat")
fun Calendar.hourMinuteFormat(): String {
    return SimpleDateFormat(HOUR_MINUTE_FORMAT).format(Date(this.timeInMillis))
}

fun Calendar.isTodayDate(): Boolean {
    return monthDateYearFormat() == Calendar.getInstance().monthDateYearFormat()
}