package caribehostal.caseroclient.dataaccess;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import caribehostal.caseroclient.datamodel.Action;
import caribehostal.caseroclient.datamodel.ActionClient;
import caribehostal.caseroclient.datamodel.ActionState;
import caribehostal.caseroclient.datamodel.ActionType;
import caribehostal.caseroclient.datamodel.Client;

/* This class is automatically generated. Do not modify it. */
public class DatabaseSetup {


    public DatabaseSetup() {

    }

    private void cleanDatabase() {
        File dbFile = DataStoreHolder.INSTANCE.getDbFile();
        File directory = dbFile.getParentFile();
        directory.mkdirs();
        if (dbFile.exists()) {
            dbFile.delete();
        }
    }

    public void mockDatabase() {
        cleanDatabase();
//        List<Owner> owners = getOwners();
        List<Client> clients = getClients();
        List<Action> actions = getActions();
        List<ActionClient> actionClients = getActionClients(actions, clients);

        DaoClient daoClient = new DaoClient();
        for (Client client : clients) {
            daoClient.upsertClient(client);
        }
        DaoAction daoAction = new DaoAction();
        for (Action action : actions) {
            daoAction.upsertAction(action);
        }
        DaoActionClient daoActionClient = new DaoActionClient();
        for (ActionClient actionClient :
                actionClients) {
            daoActionClient.upsertAction(actionClient);
        }
    }


    private List<Client> getClients() {
        List<Client> clients = new ArrayList<>();

        clients.add(new Client().setPassport("123456780"));
        clients.add(new Client().setPassport("123456781"));
        clients.add(new Client().setPassport("123456782"));
        clients.add(new Client().setPassport("123456783"));
        clients.add(new Client().setPassport("123456784"));
//        clients.add(new Client().setPassport("123456785"));

        return clients;
    }


    private List<Action> getActions() {
        List<Action> actions = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            actions.add(new Action()
                    .setActioState(ActionState.PENDING).setActionType(ActionType.INSERT)
                    .setCheckIn(LocalDate.of(2017,3,i)).setCheckOut(LocalDate.of(2017,3,i+3)).setTimeSendAction(LocalDateTime.now()));
        }
        return actions;
    }

    private List<ActionClient> getActionClients(List<Action> actions, List<Client> clients) {
        ArrayList<ActionClient> actionClients = new ArrayList<>();
        for (int i = 0; i < actions.size(); i++) {
            for (int j = 0; j <= i; j ++) {
                actionClients.add(new ActionClient().setAction(actions.get(i)).setClient(clients.get(j)));
            }
        }
        return actionClients;
    }

//    public boolean testExistAction() {
//
////        Client client = new Client().setPassport("123456780");
//        Owner owner = new Owner().setFullName("Asiel Alonso").setAddress("Boliñía")
//                .setAddressDescription("Ciego").setCarnetId("90062538346")
//                .setCell("545204265").setPassword("asio").setUser("S0100");
//        Action action = new Action().setOwner(owner)
//                .setActionState(ActionState.PENDING).setActionType(ActionType.INSERT)
//                .setCheckIn(LocalDate.of(2017,8,22)).setCheckOut(LocalDate.of(2017,8,22)).setDateAction(LocalDate.of(2017,8,22))
//                .setPetitionOwnerId("3");
//        DaoAction daoAction = new DaoAction();
//        boolean existAction = daoAction.existAction(action);
//        Log.e("existAction", existAction + "");
//        return existAction;
//    }

}
