@file:JvmName("DateTimeFormatters")
package caribehostal.caseroclient.util

import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

/**
 * @author rainermf
 */

@JvmField val LONG_DATE: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
@JvmField val MEDIUM_DATE: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
@JvmField val SHORT_TIME: DateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
