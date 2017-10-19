package caribehostal.caseroclient.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import caribehostal.caseroclient.view.tray.Permissions;
import caribehostal.caseroserver.comunication.SmsSender;

import static android.Manifest.permission.SEND_SMS;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static caribehostal.caseroclient.view.tray.PermissionsKt.REQUEST_RESEND_ACTION_SMS;
import static caribehostal.caseroclient.view.tray.TrayActivityKt.NEW_ACTION_ID;

public class RegisterClientController extends AppCompatActivity {
    @BindView(R.id.register_content)
    FrameLayout content;
    @BindView(R.id.bt_1_register)
    Button back;
    @BindView(R.id.bt_2_register)
    Button next;
    private RegisterPanel currentPanelSelect;

    private RegisterPanelDate registerPanelDate;
    private RegisterPanelAdd registerAddPanel;
    private RegisterPanelSend registerSendPanel;

    private List<Client> clients;

    public RegisterPanelDate getRegisterPanelDate() {
        return registerPanelDate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_client_activity);
        ButterKnife.bind(this);
        init();
        event();  // siempre despues de init()
    }

    private void event() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionBack();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionNext();
            }
        });

    }

    private void actionNext() {
        if (currentPanelSelect instanceof RegisterPanelDate) {
            currentPanelSelect.outPanel();
            next.setText("Siguiente");
            back.setText("Atrás");
            showClientPanelAction();
            return;
        }
        if (currentPanelSelect instanceof RegisterPanelAdd) {
            currentPanelSelect.outPanel();
            next.setText("Enviar");
            back.setText("Atrás");
            showSendPanelAction();
            return;
        }
        if (currentPanelSelect instanceof RegisterPanelSend) {
            currentPanelSelect.outPanel();
            sendAction();
            finish();
            return;
        }
}

    private void actionBack() {
        if (currentPanelSelect instanceof RegisterPanelDate) {
            currentPanelSelect.outPanel();
            finish();
            return;
        }
        if (currentPanelSelect instanceof RegisterPanelAdd) {
            currentPanelSelect.outPanel();
            back.setText("Salir");
            next.setText("Siguiente");
            showCheckinPanelAction();
            return;
        }
        if (currentPanelSelect instanceof RegisterPanelSend) {
            currentPanelSelect.outPanel();
            back.setText("Atrás");
            next.setText("Siguiente");
            showClientPanelAction();
            return;
        }
    }

    private void init() {
        clients = new ArrayList<>(10);
        registerPanelDate = new RegisterPanelDate(this);
        registerAddPanel = new RegisterPanelAdd(this);
        registerSendPanel = new RegisterPanelSend(this);
        showCheckinPanelAction();
        currentPanelSelect = registerPanelDate;
    }

    public List<Client> getClients() {
        return clients;
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

    public void outClientPanelAction() {
        content.removeAllViews();
    }

    public void outSendPanelAction() {
        content.removeAllViews();
        setEnableButtonSend(true);
    }

    public void showAddClientDialogAction() {
        AddPasspotDialog addPasspotDialog = new AddPasspotDialog(this);
        addPasspotDialog.show();
    }

    public void addClientListAction(String passport) {
        Client client = new Client().setPassport(passport);
        if (!clients.contains(client)) {
            clients.add(client);
            registerAddPanel.updateList();
        } else {
            Toast.makeText(this, "Ya existe el pasaporte", Toast.LENGTH_SHORT).show();
        }

    }

    public void setEnableButtonSend(boolean b){
        next.setEnabled(b);
    }
    public void sendAction() {
        if (clients.isEmpty()) {
            return;
        }
        if (registerPanelDate.getCheckin().isAfter(registerPanelDate.getCheckout())) {
            return;
        }

        if (ContextCompat.checkSelfPermission(this, SEND_SMS) == PERMISSION_GRANTED) {
            Action action = saveDates();
//            new SmsSender().sendSms(action);

            Intent result = new Intent();
            result.putExtra(NEW_ACTION_ID, action.getId());
            setResult(RESULT_OK, result);
            finish();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{SEND_SMS}, REQUEST_RESEND_ACTION_SMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Permissions.INSTANCE.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
