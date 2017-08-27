package caribehostal.caseroclient.dataaccess;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import caribehostal.caseroclient.datamodel.Action;
import caribehostal.caseroclient.datamodel.ActionClient;
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
//        List<Action> actions = getActions(owners, clients);
//        List<ActionClient> actionClients = getActionClients(actions, clients);
//
//        DaoOwner daoOwner = new DaoOwner();
//        for (Owner owner : owners) {
//            daoOwner.upsertOwner(owner);
//        }
        DaoClient daoClient = new DaoClient();
        for (Client client : clients) {
            daoClient.upsertClient(client);
        }
//        DaoAction daoAction = new DaoAction();
//        for (Action action : actions) {
//            daoAction.upsertAction(action);
//        }
//        DaoActionClient daoActionClient = new DaoActionClient();
//        for (ActionClient actionClient :
//                actionClients) {
//            daoActionClient.upsertAction(actionClient);
//        }
    }


    private List<Client> getClients() {
        List<Client> clients = new ArrayList<>();

        clients.add(new Client().setPassport("123456780"));
        clients.add(new Client().setPassport("123456781"));
        clients.add(new Client().setPassport("123456782"));
        clients.add(new Client().setPassport("123456783"));
        clients.add(new Client().setPassport("123456784"));
        clients.add(new Client().setPassport("123456785"));

        return clients;
    }


//    private List<Action> getActions(List<Client> clients) {
//        List<Action> actions = new ArrayList<>();
//        for (int i = 0; i < owners.size(); i++) {
//            actions.add(new Action().setOwner(owners.get(i))
//                    .setActionState(ActionState.PENDING).setActionType(ActionType.INSERT)
//                    .setCheckIn(LocalDate.now()).setCheckOut(LocalDate.now()).setDateAction(LocalDate.now())
//                    .setPetitionOwnerId("1"));
//            actions.add(new Action().setOwner(owners.get(i))
//                    .setActionState(ActionState.PENDING).setActionType(ActionType.INSERT)
//                    .setCheckIn(LocalDate.now()).setCheckOut(LocalDate.now()).setDateAction(LocalDate.now())
//                    .setPetitionOwnerId("2"));
//        }
//        return actions;
//    }

    private List<ActionClient> getActionClients(List<Action> actions, List<Client> clients) {
        ArrayList<ActionClient> actionClients = new ArrayList<>();
        for (int i = 0; i < actions.size(); i++) {
            for (Client client :  clients) {
                actionClients.add(new ActionClient().setAction(actions.get(i)).setClient(client));
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
