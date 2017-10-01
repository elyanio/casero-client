package caribehostal.caseroclient.dataaccess;

import java.util.List;

import caribehostal.caseroclient.datamodel.Client;
import caribehostal.caseroclient.datamodel.Develop;
import io.requery.Persistable;
import io.requery.query.Result;
import io.requery.sql.EntityDataStore;

/**
 * Created by asio on 8/17/2017.
 */

public class DaoDevelop {
    private EntityDataStore<Persistable> dataStore;

    public DaoDevelop() {
        dataStore = DataStoreHolder.INSTANCE.getDataStore();
    }

    public void upsertDevelop(Develop develops) {
        dataStore.upsert(develops);
    }

    public void upsertDevelopss(List<Develop> develops) {
        for (Develop develop : develops) {
            upsertDevelop(develop);
        }
    }

    public Result<Develop> getAllDevelops() {
        return dataStore.select(Develop.class).get();

    }

    public void removeAllDevelop(List<Develop> develops) {
        dataStore.delete(develops);
    }
}
