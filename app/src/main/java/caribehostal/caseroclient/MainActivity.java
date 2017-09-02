package caribehostal.caseroclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import caribehostal.caseroclient.controllers.RegisterClientController;
import caribehostal.caseroclient.dataaccess.DatabaseSetup;
import caribehostal.caseroclient.view.tray.TrayActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button register;
    @BindView(R.id.button2)
    Button mensaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        DatabaseSetup databaseSetup = new DatabaseSetup();
//        databaseSetup.mockDatabase();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegister();
            }

        });
        mensaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMensaj();
            }
        });


    }

    private void showRegister() {
        startActivity(new Intent(this, RegisterClientController.class));
    }

    private void showMensaj() {
        startActivity(new Intent(this, TrayActivity.class));
    }
}
