package caribehostal.caseroclient.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
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

import static caribehostal.caseroclient.R.drawable.ic_home_black_24dp;
import static caribehostal.caseroclient.R.drawable.ic_notifications_black_24dp;

public class RegisterClient extends AppCompatActivity {
    @BindView(R.id.register_content)
    FrameLayout content;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_checkin:
                    if (!(currentPanelSelect instanceof RegisterPanelCheckin)) {
                        //                    mTextMessage.setText(R.string.title_home);
                        currentPanelSelect.outPanel();
                        showCheckinPanelAction();
                        currentItemSelect = item;
                    }

                    return true;
                case R.id.navigation_checkout:
                    if (!(currentPanelSelect instanceof RegisterPanelCheckout)) {
                        //                    mTextMessage.setText(R.string.title_dashboard);
                        currentPanelSelect.outPanel();
                        showCheckoutPanelAction();
                        currentItemSelect = item;
                    }
                    return true;
                case R.id.navigation_client:
                    if (!(currentPanelSelect instanceof RegisterPanelAdd)) {
                        //                    mTextMessage.setText(R.string.title_notifications);
                        item.setIcon(ic_home_black_24dp);
                        currentPanelSelect.outPanel();
                        showClientPanelAction();
                        currentItemSelect = item;
                    } else {
                        addClientAction();
                    }
                    return true;
                case R.id.navigation_ok:
//                    mTextMessage.setText(R.string.title_notifications);

                    currentItemSelect = item;
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
    private LocalDate checkin;
    private LocalDate checkout;
    private List<Client> clients = new ArrayList<Client>(10);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_client_activity);
        ButterKnife.bind(this);
        init();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        showCheckinPanelAction();
    }

    private void init() {
        registerCheckinPanel = new RegisterPanelCheckin(this, LocalDate.now());
        registerCheckoutPanel = new RegisterPanelCheckout(this, LocalDate.now());
        DaoClient daoClient = new DaoClient();
        daoClient.getAllClient().collect(this.clients);
        registerAddPanel = new RegisterPanelAdd(this, clients);

        currentPanelSelect = registerCheckinPanel;
    }

    public RegisterPanelCheckin getRegisterCheckinPanel() {
        return registerCheckinPanel;
    }

    public RegisterPanelCheckout getRegisterCheckoutPanel() {
        return registerCheckoutPanel;
    }

    public RegisterPanelAdd getRegisterAddPanel() {
        return registerAddPanel;
    }


    // action del controlador
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

    public void outCheckinPanelAction() {
        checkin = registerCheckinPanel.getLocalDate();
        content.removeAllViews();
    }

    public void outCheckoutPanelAction() {
        checkout = registerCheckoutPanel.getLocalDate();
        content.removeAllViews();
    }

    public void outClientPanelAction() {
        content.removeAllViews();
        currentItemSelect.setIcon(ic_notifications_black_24dp);
    }

    public void addClientAction() {
        AddPasspotDialog addPasspotDialog = new AddPasspotDialog(this);
        addPasspotDialog.show();
    }

    public void saveClientAction(String passport) {
        Client client = new Client().setPassport(passport);
        DaoClient daoClient = new DaoClient();
        daoClient.upsertClient(client);
        clients.add(client);
        registerAddPanel.updateList();
    }
}
