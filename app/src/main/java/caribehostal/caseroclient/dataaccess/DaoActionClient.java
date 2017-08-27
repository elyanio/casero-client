package caribehostal.caseroclient.dataaccess;

import caribehostal.caseroclient.datamodel.ActionClient;
import io.requery.Persistable;
import io.requery.sql.EntityDataStore;

/**
 * Created by Fernando on 22/08/2017.
 */

public class DaoActionClient {

    private EntityDataStore<Persistable> dataStore;

    public DaoActionClient() {
        dataStore = DataStoreHolder.INSTANCE.getDataStore();
    }

    public void upsertAction(ActionClient actionClient) {
        dataStore.upsert(actionClient);
    }
}
