package caribehostal.caseroclient.dataaccess;

import org.threeten.bp.LocalDateTime;

import java.util.List;

import caribehostal.caseroclient.datamodel.Action;
import caribehostal.caseroclient.datamodel.ActionState;
import io.requery.Persistable;
import io.requery.sql.EntityDataStore;

/**
 * @author asio
 */
public class DaoAction {
    EntityDataStore<Persistable> dataStore;

    public DaoAction() {
        dataStore = DataStoreHolder.INSTANCE.getDataStore();
    }

    public void deleteAction(Action action) {
        dataStore.delete(action);
    }

    public void deleteAction(int id) {
        dataStore.delete(Action.class).where(Action.ID.eq(id)).get().value();
    }

    public Action upsertAction(Action action) {
        return dataStore.upsert(action);
    }

    List<Action> _getAllActions() {
        return dataStore.select(Action.class)
                .get()
                .toList();
    }

    List<Action> _getPendingActions() {
        return getActionsByState(ActionState.PENDING);
    }

    List<Action> _getConfirmedActions() {
        return getActionsByState(ActionState.FINISH);
    }

    private List<Action> getActionsByState(ActionState state) {
        return dataStore.select(Action.class)
                .where(Action.ACTIO_STATE.eq(state))
                .get()
                .toList();
    }

    public Action getAction(Integer id) {
        return dataStore.select(Action.class).where(Action.ID.eq(id)).get().first();
    }

    public Action updateResponseTime(int id, LocalDateTime responseTime) {
        dataStore.update(Action.class).set(Action.RESPONSE_TIME, responseTime)
                .where(Action.ID.eq(id)).get().value();
        return getAction(id);
    }

    public Action updateState(int id, ActionState actionState) {
        dataStore.update(Action.class).set(Action.ACTIO_STATE, actionState)
                .where(Action.ID.eq(id)).get().value();
        return getAction(id);
    }

    public Action updateUnread(int id, Boolean unread) {
        dataStore.update(Action.class).set(Action.UNREAD, unread)
                .where(Action.ID.eq(id)).get().value();
        return getAction(id);
    }
}
