@file:JvmName("DateUtils")

package caribehostal.caseroclient.util

import org.threeten.bp.LocalDate
import java.util.*

fun Date.toLocalDate(): LocalDate {
    return LocalDate.of(this.year + 1900, this.month + 1, this.date)
}

fun LocalDate.toDate(): Date {
    return Date(this.year - 1900, this.monthValue - 1, this.dayOfMonth)
}
