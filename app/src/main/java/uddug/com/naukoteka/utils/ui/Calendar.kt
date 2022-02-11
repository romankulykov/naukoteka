package uddug.com.naukoteka.utils.ui

import android.annotation.SuppressLint
import org.joda.time.DateTime
import org.joda.time.Days
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

fun Calendar.getYear(): Int {
    return get(Calendar.YEAR)
}

fun Calendar.getMonth(): Int {
    return get(Calendar.MONTH) + 1
}

fun Calendar.getDay(): Int {
    return get(Calendar.DAY_OF_MONTH)
}

fun Calendar.getDayOfWeek(): Int {
    return get(Calendar.DAY_OF_WEEK)
}

fun Calendar.getDateFormatChatList(): String {
    return if (isTodayDate()) {
        hourMinuteFormat()
    } else {
        val countDaysInWeek = 7
        val period = Days.daysBetween(DateTime(this), DateTime()).days
        if (period < countDaysInWeek) {
            getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())
        } else {
            monthDateYearFormat()
        }
    }
}