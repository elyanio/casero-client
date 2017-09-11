package caribehostal.caseroclient.datamodel

import caribehostal.caseroclient.datamodel.ActionState.FINISH
import caribehostal.caseroclient.datamodel.ActionState.PENDING
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

/**
 * @author rainermf
 */
data class FullAction(
        val id: Int,
        val sendTime: LocalDateTime,
        val responseTime: LocalDateTime?,
        val checkIn: LocalDate,
        val checkOut: LocalDate,
        val type: ActionType,
        val state: ActionState,
        val unread: Boolean,
        val passports: List<String>,
        val responseCodes: List<String>
) {
    val updateTime = if(state == FINISH && responseTime != null) responseTime else sendTime
}