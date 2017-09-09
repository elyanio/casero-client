package caribehostal.caseroclient.dataaccess

import caribehostal.caseroclient.datamodel.Action
import caribehostal.caseroclient.datamodel.ActionClient
import caribehostal.caseroclient.datamodel.ClientInfo
import caribehostal.caseroclient.datamodel.FullAction

/**
 * @author rainermf
 */
fun DaoActionClient.getClientInfo(action: Action) = dataStore.select(ActionClient::class.java)
        .where(ActionClient.ACTION.eq(action))
        .get()
        .toList()
        .map { ClientInfo(it.client.passport, it.responseCode ?: "") }

fun DaoAction.getPassports(action: Action): List<String> = dataStore.select(ActionClient::class.java)
        .where(ActionClient.ACTION.eq(action))
        .orderBy(ActionClient.ID)
        .get()
        .toList()
        .map { it.client.passport }

fun DaoAction.getResponseCodes(action: Action): List<String> = dataStore.select(ActionClient::class.java)
        .where(ActionClient.ACTION.eq(action))
        .orderBy(ActionClient.ID)
        .get()
        .toList()
        .map { it.responseCode ?: "" }

fun DaoAction.loadAllActions() = _getAllActions().map { fullAction(it) }
fun DaoAction.loadPendingActions() = _getPendingActions().map { fullAction(it) }
fun DaoAction.loadConfirmedActions() = _getConfirmedActions().map { fullAction(it) }

private fun DaoAction.fullAction(it: Action): FullAction = FullAction(
        id = it.id,
        checkIn = it.checkIn,
        checkOut = it.checkOut,
        responseTime = it.responseTime,
        sendTime = it.sendTime,
        state = it.actioState,
        type = it.actionType,
        unread = it.isUnread,
        passports = getPassports(it),
        responseCodes = getResponseCodes(it)
)