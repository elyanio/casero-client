package caribehostal.caseroclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import caribehostal.caseroclient.controllers.RegisterClientController;
import caribehostal.caseroclient.dataaccess.DatabaseSetup;
import caribehostal.caseroclient.view.about.AboutActivity;
import caribehostal.caseroclient.view.tray.TrayActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        DatabaseSetup databaseSetup = new DatabaseSetup();
//        databaseSetup.mockDatabase();
    }

    @OnClick(R.id.card_view_show_message)
    void showOwnerView() {
        startActivity(new Intent(this, TrayActivity.class));
    }

    @OnClick(R.id.card_view_new_petitions)
    void showRegisterView() {
        startActivity(new Intent(this, RegisterClientController.class));
    }

    @OnClick(R.id.card_view_contacts)
    void showSettingsView() {
        startActivity(new Intent(this, AboutActivity.class));
    }

}
