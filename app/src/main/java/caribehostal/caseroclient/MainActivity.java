package caribehostal.caseroclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import caribehostal.caseroclient.controllers.RegisterClientController;
import caribehostal.caseroclient.dataaccess.DatabaseSetup;
import caribehostal.caseroclient.settings.Settings;
import caribehostal.caseroclient.view.about.AboutActivity;
import caribehostal.caseroclient.view.about.DevelopActivity;
import caribehostal.caseroclient.view.about.TermsActivity;
import caribehostal.caseroclient.view.about.TermsShowActivity;
import caribehostal.caseroclient.view.registerserver.StageRegisterServer;
import caribehostal.caseroclient.view.tray.TrayActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.card_view_show_message)
    android.support.v7.widget.CardView menssage;
    @BindView(R.id.card_view_new_petitions)
    android.support.v7.widget.CardView newPetition;
    @BindView(R.id.image_view_action)
    ImageView imageMenssage;
    @BindView(R.id.image_view_owner)
    ImageView imageNewPetition;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        message = (TextView) findViewById(R.id.count_message);
        ButterKnife.bind(this);
        DatabaseSetup databaseSetup = new DatabaseSetup();
        databaseSetup.mockDatabase();
    }

    @OnClick(R.id.card_view_show_message)
    void showOwnerView() {
        if (Settings.isApkActivated()) {
            if (Settings.isTermsAcepted()) {
                startActivity(new Intent(this, TrayActivity.class));
            } else {
                startActivity(new Intent(this, TermsActivity.class));
                finish();
            }
        } else {
            if (Settings.isRegisterServerSend()) {
                Toast.makeText(this, "Por favor espere respuesta de nuestro servidor", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, StageRegisterServer.class));
                finish();
            }
        }
    }


    @OnClick(R.id.card_view_new_petitions)
    void showRegisterView() {
        if (Settings.isApkActivated()) {
            if (Settings.isTermsAcepted()) {
                startActivity(new Intent(this, RegisterClientController.class));
            } else {
                startActivity(new Intent(this, TermsActivity.class));
                finish();
            }
        } else {
            if (Settings.isRegisterServerSend()) {
                Toast.makeText(this, "Por favor espere respuesta de nuestro servidor", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, StageRegisterServer.class));
                finish();
            }
        }
    }

    @OnClick(R.id.card_view_show_pay_contacts)
    void showCreatorView() {
        startActivity(new Intent(this, DevelopActivity.class));
    }

    @OnClick(R.id.card_view_contacts)
    void showSettingsView() {
        startActivity(new Intent(this, TermsShowActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        message.setText(Settings.getDontSeeMessage() + "");
    }
}
