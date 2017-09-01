package caribehostal.caseroclient.dataaccess;

import java.util.List;

import caribehostal.caseroclient.datamodel.Action;
import caribehostal.caseroclient.datamodel.ActionState;
import io.requery.Persistable;
import io.requery.sql.EntityDataStore;

/**
 * @author asio
 */
public class DaoAction {
    private EntityDataStore<Persistable> dataStore;

    public DaoAction() {
        dataStore = DataStoreHolder.INSTANCE.getDataStore();
    }

    public void upsertAction(Action action) {
        dataStore.upsert(action);
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
}
