package caribehostal.caseroclient.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import caribehostal.caseroclient.R;
import caribehostal.caseroclient.dataaccess.DaoAction;
import caribehostal.caseroclient.dataaccess.DaoActionClient;
import caribehostal.caseroclient.dataaccess.DaoClient;
import caribehostal.caseroclient.datamodel.Action;
import caribehostal.caseroclient.datamodel.ActionClient;
import caribehostal.caseroclient.datamodel.ActionState;
import caribehostal.caseroclient.datamodel.ActionType;
import caribehostal.caseroclient.datamodel.Client;
import caribehostal.caseroclient.view.registerclient.AddPasspotDialog;
import caribehostal.caseroclient.view.registerclient.RegisterPanel;
import caribehostal.caseroclient.view.registerclient.RegisterPanelAdd;
import caribehostal.caseroclient.view.registerclient.RegisterPanelDate;
import caribehostal.caseroclient.view.registerclient.RegisterPanelSend;
import caribehostal.caseroclient.comunication.SmsSender;

public class RegisterClientController extends AppCompatActivity {
    @BindView(R.id.register_content)
    FrameLayout content;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dates:
                    if (!(currentPanelSelect instanceof RegisterPanelDate)) {
                        currentPanelSelect.outPanel();
                        showCheckinPanelAction();
                        currentItemSelect = item;
                    }

                    return true;
                case R.id.navigation_client:
                    if (!(currentPanelSelect instanceof RegisterPanelAdd)) {
                        currentPanelSelect.outPanel();
                        showClientPanelAction();
                        currentItemSelect = item;
                    } else {

                    }
                    return true;
                case R.id.navigation_ok:
                    if (!(currentPanelSelect instanceof RegisterPanelSend)) {
                        currentPanelSelect.outPanel();
                        showSendPanelAction();
                        currentItemSelect = item;
                    } else {

                    }
                    return true;
            }
            return false;
        }

    };
    private BottomNavigationView navigation;
    private MenuItem currentItemSelect;
    private RegisterPanel currentPanelSelect;

    private RegisterPanelDate registerPanelDate;
    private RegisterPanelAdd registerAddPanel;
    private RegisterPanelSend registerSendPanel;

    private List<Client> clients;

    public RegisterPanelDate getRegisterPanelDate() {
        return registerPanelDate;
    }

    public RegisterPanelAdd getRegisterAddPanel() {
        return registerAddPanel;
    }

    public RegisterPanelSend getRegisterSendPanel() {
        return registerSendPanel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_client_activity);
        ButterKnife.bind(this);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        init();
    }

    private void init() {
        clients = new ArrayList<Client>(10);

        registerPanelDate = new RegisterPanelDate(this);
        registerAddPanel = new RegisterPanelAdd(this);
        registerSendPanel = new RegisterPanelSend(this);

        showCheckinPanelAction();
        currentPanelSelect = registerPanelDate;


    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    private Action saveDates() {
        DaoAction daoAction = new DaoAction();
        Action action = daoAction.upsertAction(new Action().setActionType(ActionType.INSERT).setActioState(ActionState.PENDING)
                .setSendTime(LocalDateTime.now()).setCheckIn(registerPanelDate.getCheckin())
                .setCheckOut(registerPanelDate.getCheckout()));

        DaoClient daoClient = new DaoClient();
        daoClient.upsertClients(clients);

        DaoActionClient daoActionClient = new DaoActionClient();
        for (Client client : clients) {
            ActionClient actionClient = new ActionClient().setAction(action).setClient(client);
            daoActionClient.upsertAction(actionClient);
        }

        return action;
    }


    // acciones del controlador
    private void showCheckinPanelAction() {
        content.addView(registerPanelDate);
        currentPanelSelect = registerPanelDate;
    }

    private void showClientPanelAction() {
        content.addView(registerAddPanel);
        currentPanelSelect = registerAddPanel;
    }

    private void showSendPanelAction() {
        registerSendPanel.updateText();
        content.addView(registerSendPanel);
        currentPanelSelect = registerSendPanel;
    }

    public void outCheckinPanelAction() {
        content.removeAllViews();
    }

    public void outCheckoutPanelAction() {
        content.removeAllViews();
    }

    public void outClientPanelAction() {
        content.removeAllViews();
    }

    public void outSendPanelAction() {
        content.removeAllViews();
    }

    public void showAddClientDialogAction() {
        AddPasspotDialog addPasspotDialog = new AddPasspotDialog(this);
        addPasspotDialog.show();
    }

    public void addClientListAction(String passport) {
        Client client = new Client().setPassport(passport);
        if(!clients.contains(client)){
            clients.add(client);
            registerAddPanel.updateList();
        }else{
            Toast.makeText(this,"Ya existe el pasaporte", Toast.LENGTH_SHORT).show();
        }

    }

    public void sendAction() {
        if (clients.isEmpty()) {
            //torta hacer algo
            return;
        }
        if (registerPanelDate.getCheckin().isAfter(registerPanelDate.getCheckout())) {
            // torta hacer algo
            return;
        }
        Action action = saveDates();
        SmsSender smsSender = new SmsSender();
        smsSender.sendSms(action);
        finish();
    }


}
