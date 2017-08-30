package caribehostal.caseroclient.dataaccess;

//import caribehostal.caseroserver.caribehostal.caseroclient.datamodel.Action;

import caribehostal.caseroclient.datamodel.Action;
import io.requery.Persistable;
import io.requery.query.Result;
import io.requery.sql.EntityDataStore;


/**
 * Created by asio on 8/17/2017.
 */

public class DaoAction {
    private EntityDataStore<Persistable> dataStore;

    public DaoAction() {
        dataStore = DataStoreHolder.INSTANCE.getDataStore();
    }

    public void upsertAction(Action action) {
        dataStore.upsert(action);
    }

    public Result<Action> getAllAction(){
        return dataStore.select(Action.class).orderBy(Action.TIME_SEND_ACTION).get();
    }
}
