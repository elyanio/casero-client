package caribehostal.caseroclient.dataaccess;

//import caribehostal.caseroserver.caribehostal.caseroclient.datamodel.Action;

import caribehostal.caseroclient.datamodel.Action;
import io.requery.Persistable;
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

//    public boolean existAction(Action action) {
//        Action act = dataStore.select(Action.class).join(Owner.class).on(Action.OWNER_ID.eq(CARNET_ID))
//                .where(Action.PETITION_OWNER_ID.eq(action.getPetitionOwnerId())).and(Owner.CELL.eq(action.getOwner().getCell())).get().firstOrNull();
//        if (act == null) {
//            return false;
//        }
//        return true;
//    }
}
