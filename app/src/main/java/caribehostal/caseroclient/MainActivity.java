package caribehostal.caseroclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import caribehostal.caseroclient.controllers.DashboardController;
import caribehostal.caseroclient.controllers.RegisterClientController;
import caribehostal.caseroclient.dataaccess.DatabaseSetup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseSetup databaseSetup = new DatabaseSetup();
        databaseSetup.mockDatabase();
//        startActivity(new Intent(this, RegisterClientController.class));
        startActivity(new Intent(this, DashboardController.class));

    }
}
