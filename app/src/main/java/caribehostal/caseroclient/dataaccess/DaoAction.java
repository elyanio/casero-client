package caribehostal.caseroclient.dataaccess;

import org.threeten.bp.LocalDateTime;

import java.util.List;

import caribehostal.caseroclient.datamodel.Action;
import caribehostal.caseroclient.datamodel.ActionState;
import caribehostal.caseroclient.datamodel.LocalDateTimeConverter;
import io.requery.Persistable;
import io.requery.meta.Attribute;
import io.requery.sql.EntityDataStore;

/**
 * @author asio
 */
public class DaoAction {
    private EntityDataStore<Persistable> dataStore;

    public DaoAction() {
        dataStore = DataStoreHolder.INSTANCE.getDataStore();
    }

    public Action upsertAction(Action action) {
        return dataStore.upsert(action);
    }

    public List<Action> getAllActions(){
        return dataStore.select(Action.class)
                .orderBy(Action.SEND_TIME)
                .get()
                .toList();
    }

    public List<Action> getPendingActions() {
        return getActionsByState(ActionState.PENDING);
    }

    public List<Action> getConfirmedActions() {
        return getActionsByState(ActionState.FINISH);
    }

    private List<Action> getActionsByState(ActionState state) {
        return dataStore.select(Action.class)
                .where(Action.ACTIO_STATE.eq(state))
                .get()
                .toList();
    }

    public Action getAction(int id){
        return dataStore.select(Action.class).where(Action.ID.eq(id)).get().firstOrNull();
    }

    public Action update(int id, LocalDateTime responseTime){
        dataStore.update(Action.class).set(Action.RESPONSE_TIME, responseTime)
                .where(Action.ID.eq(id));
        return getAction(id);
    }

    public Action finishAction(int id){
        dataStore.update(Action.class).set(Action.ACTIO_STATE, ActionState.FINISH)
                .where(Action.ID.eq(id));
        return getAction(id);
    }
}
