package caribehostal.caseroclient.dataaccess;

import java.util.List;

import caribehostal.caseroclient.datamodel.Client;
import io.requery.Persistable;
import io.requery.query.Result;
import io.requery.sql.EntityDataStore;

/**
 * Created by asio on 8/17/2017.
 */

public class DaoClient {
    private EntityDataStore<Persistable> dataStore;

    public DaoClient() {
        dataStore = DataStoreHolder.INSTANCE.getDataStore();
    }

    public void upsertClient(Client client) {
        dataStore.upsert(client);
    }

    public void upsertClients(List<Client> clients) {
        for (Client client : clients) {
            upsertClient(client);
        }
    }

    public Result<Client> getAllClient() {
        return dataStore.select(Client.class).orderBy(Client.PASSPORT).get();

    }

    public void remove(Client client) {
        dataStore.delete(client);
    }
}
