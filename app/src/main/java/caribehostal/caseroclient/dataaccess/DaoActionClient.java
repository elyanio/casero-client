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

    EntityDataStore<Persistable> dataStore;

    public DaoActionClient() {
        dataStore = DataStoreHolder.INSTANCE.getDataStore();
    }

    public void upsertAction(ActionClient actionClient) {
        dataStore.upsert(actionClient);
    }

    public void updateConfirmationCode(ActionClient actionClient, String code) {
        dataStore.update(ActionClient.class).set(ActionClient.RESPONSE_CODE, code)
                .where(ActionClient.ID.eq(actionClient.getId())).get().value();
    }

    public Result<Client> getClients(Action action) {
        return dataStore.select(Client.class)
                .join(ActionClient.class).on(Client.PASSPORT.eq(ActionClient.CLIENT_ID))
                .where(ActionClient.ACTION.eq(action)).orderBy(ActionClient.ID)
                .get();
    }

    public List<ActionClient> getActionClient(Action action) {
        return dataStore.select(ActionClient.class).where(ActionClient.ACTION_ID.eq(action.getId()))
                .orderBy(ActionClient.ID).get().toList();
    }
}
