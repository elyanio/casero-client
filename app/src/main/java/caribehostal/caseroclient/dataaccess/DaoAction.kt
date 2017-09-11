package caribehostal.caseroclient.dataaccess

import org.threeten.bp.LocalDateTime

import caribehostal.caseroclient.datamodel.Action
import caribehostal.caseroclient.datamodel.ActionClient
import caribehostal.caseroclient.datamodel.ActionState
import caribehostal.caseroclient.datamodel.FullAction
import io.requery.Persistable
import io.requery.query.Result
import io.requery.sql.EntityDataStore

/**
 * @author asio
 */
class DaoAction {
    val dataStore: EntityDataStore<Persistable> = DataStoreHolder.INSTANCE.dataStore

    fun deleteAction(id: Int) {
        dataStore.delete(Action::class.java).where(Action.ID.eq(id)).get().value()
    }

    fun upsertAction(action: Action): Action {
        return dataStore.upsert(action)
    }

    internal fun _getAllActions(): Result<Action> {
        return dataStore.select(Action::class.java).get()
    }

    fun getAction(id: Int): Action {
        return dataStore.select(Action::class.java).where(Action.ID.eq(id)).get().first()
    }

    fun updateResponseTime(id: Int, responseTime: LocalDateTime): Action {
        dataStore.update(Action::class.java).set(Action.RESPONSE_TIME, responseTime)
                .where(Action.ID.eq(id)).get().value()
        return getAction(id)
    }

    fun updateState(id: Int, actionState: ActionState): Action {
        dataStore.update(Action::class.java).set(Action.ACTIO_STATE, actionState)
                .where(Action.ID.eq(id)).get().value()
        return getAction(id)
    }

    fun updateUnread(id: Int, unread: Boolean?): Action {
        dataStore.update(Action::class.java).set(Action.UNREAD, unread)
                .where(Action.ID.eq(id)).get().value()
        return getAction(id)
    }

    fun getPassports(action: Action): List<String> = dataStore.select(ActionClient::class.java)
            .where(ActionClient.ACTION.eq(action))
            .orderBy(ActionClient.ID)
            .get()
            .toList()
            .map { it.client.passport }

    fun getResponseCodes(action: Action): List<String> = dataStore.select(ActionClient::class.java)
            .where(ActionClient.ACTION.eq(action))
            .orderBy(ActionClient.ID)
            .get()
            .toList()
            .map { it.responseCode ?: "" }

    fun getFullAction(actionId: Int) = fullAction(getAction(actionId))
    fun loadAllActions() = _getAllActions().map { fullAction(it) }

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

    fun getLastActions(currentActionsAmount: Int): List<FullAction> {
        val newActionsAmount = dataStore.count(Action::class.java).get().value() - currentActionsAmount
        if (newActionsAmount == 0) return emptyList()
        return dataStore.select(Action::class.java)
                .orderBy(Action.ID.desc())
                .limit(newActionsAmount)
                .get().map { fullAction(it) }
    }
}
