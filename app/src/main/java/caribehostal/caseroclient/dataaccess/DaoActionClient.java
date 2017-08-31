package caribehostal.caseroclient.dataaccess;

import java.util.List;

import caribehostal.caseroclient.datamodel.Action;
import caribehostal.caseroclient.datamodel.ActionClient;
import caribehostal.caseroclient.datamodel.Client;
import io.requery.Persistable;
import io.requery.query.Result;
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

    public List<Client> getClients(Action action){
        return dataStore.select(Client.class)
                .join(ActionClient.class).on(Client.PASSPORT.eq(ActionClient.CLIENT_ID))
                .where(ActionClient.ACTION.eq(action))
                .get()
                .toList();
    }
}
