package caribehostal.caseroclient.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import caribehostal.caseroclient.R;
import caribehostal.caseroclient.dataaccess.DaoClient;
import caribehostal.caseroclient.datamodel.Client;
import caribehostal.caseroclient.view.registerclient.AddPasspotDialog;
import caribehostal.caseroclient.view.registerclient.RegisterPanel;
import caribehostal.caseroclient.view.registerclient.RegisterPanelAdd;
import caribehostal.caseroclient.view.registerclient.RegisterPanelCheckin;
import caribehostal.caseroclient.view.registerclient.RegisterPanelCheckout;
import caribehostal.caseroclient.view.registerclient.RegisterPanelSend;

import static caribehostal.caseroclient.R.drawable.ic_home_black_24dp;
import static caribehostal.caseroclient.R.drawable.ic_notifications_black_24dp;

public class RegisterClientController extends AppCompatActivity {
    @BindView(R.id.register_content)
    FrameLayout content;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_checkin:
                    if (!(currentPanelSelect instanceof RegisterPanelCheckin)) {
                        currentPanelSelect.outPanel();
                        showCheckinPanelAction();
                        currentItemSelect = item;
                    }

                    return true;
                case R.id.navigation_checkout:
                    if (!(currentPanelSelect instanceof RegisterPanelCheckout)) {
                        currentPanelSelect.outPanel();
                        showCheckoutPanelAction();
                        currentItemSelect = item;
                    }
                    return true;
                case R.id.navigation_client:
                    if (!(currentPanelSelect instanceof RegisterPanelAdd)) {
                        item.setTitle(R.string.title_add_client);
                        item.setIcon(ic_home_black_24dp);
                        currentPanelSelect.outPanel();
                        showClientPanelAction();
                        currentItemSelect = item;
                    } else {
                        showAddClientDialogAction();
                    }
                    return true;
                case R.id.navigation_ok:
                    if (!(currentPanelSelect instanceof RegisterPanelSend)) {
                        item.setTitle(R.string.title_send);
                        currentPanelSelect.outPanel();
                        showSendPanelAction();
                        currentItemSelect = item;
                    } else {
                        sendAction();
                    }
                    return true;
            }
            return false;
        }

    };
    private BottomNavigationView navigation;
    private MenuItem currentItemSelect;
    private RegisterPanel currentPanelSelect;

    private RegisterPanelCheckin registerCheckinPanel;
    private RegisterPanelCheckout registerCheckoutPanel;
    private RegisterPanelAdd registerAddPanel;
    private RegisterPanelSend registerSendPanel;

    private LocalDate checkin;
    private LocalDate checkout;
    private List<Client> clients;


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
        checkin = LocalDate.now();
        checkout = LocalDate.now();
        clients = new ArrayList<Client>(10);

        registerCheckinPanel = new RegisterPanelCheckin(this);
        registerCheckoutPanel = new RegisterPanelCheckout(this);
        registerAddPanel = new RegisterPanelAdd(this);
        registerSendPanel = new RegisterPanelSend(this);

        showCheckinPanelAction();
        currentPanelSelect = registerCheckinPanel;
    }

    public List<Client> getClient() {
        return clients;
    }

    public LocalDate getCheckin() {
        return checkin;
    }

    public LocalDate getCheckout() {
        return checkout;
    }

    public void setCheckin(LocalDate checkin) {
        this.checkin = checkin;
    }

    public void setCheckout(LocalDate checkout) {
        this.checkout = checkout;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    private void sendSms() {

    }



    // acciones del controlador
    private void showCheckinPanelAction() {
        content.addView(registerCheckinPanel);
        currentPanelSelect = registerCheckinPanel;
    }

    private void showCheckoutPanelAction() {
        content.addView(registerCheckoutPanel);
        currentPanelSelect = registerCheckoutPanel;
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
        currentItemSelect.setIcon(ic_notifications_black_24dp);
    }

    public void outSendPanelAction() {
        content.removeAllViews();
        currentItemSelect.setIcon(ic_notifications_black_24dp);
    }

    public void showAddClientDialogAction() {
        AddPasspotDialog addPasspotDialog = new AddPasspotDialog(this);
        addPasspotDialog.show();
    }

    public void addClientListAction(String passport) {
        Client client = new Client().setPassport(passport);
        clients.add(client);
        registerAddPanel.updateList();
    }

    public void sendAction() {
        if (clients.isEmpty()) {
            //torta hacer algo
            return;
        }
        if (checkin.isAfter(checkout)) {
            // torta hacer algo
            return;
        }
        DaoClient daoClient = new DaoClient();
        for (Client client : clients) {
            daoClient.upsertClient(client);
        }
        sendSms();
    }


}
