package caribehostal.caseroclient.dataaccess

import caribehostal.caseroclient.datamodel.Action
import caribehostal.caseroclient.datamodel.ActionClient
import caribehostal.caseroclient.datamodel.ClientInfo

/**
 * @author rainermf
 */
fun DaoActionClient.getClientInfo(action: Action) = dataStore.select(ActionClient::class.java)
        .where(ActionClient.ACTION.eq(action))
        .get()
        .toList()
        .map { ClientInfo(it.client.passport, it.responseCode ?: "") }