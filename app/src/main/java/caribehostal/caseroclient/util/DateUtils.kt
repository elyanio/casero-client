@file:JvmName("DateUtils")

package caribehostal.caseroclient.util

import org.threeten.bp.LocalDate
import java.util.*

@Suppress("DEPRECATION")
fun Date.toLocalDate(): LocalDate {
    return LocalDate.of(this.year + 1900, this.month + 1, this.date)
}

@Suppress("DEPRECATION")
fun LocalDate.toDate(): Date {
    return Date(this.year - 1900, this.monthValue - 1, this.dayOfMonth)
}
