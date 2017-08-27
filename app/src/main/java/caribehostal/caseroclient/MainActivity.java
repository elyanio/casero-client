package caribehostal.caseroclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import caribehostal.caseroclient.dataaccess.DatabaseSetup;
import caribehostal.caseroclient.view.RegisterClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, RegisterClient.class));
        DatabaseSetup databaseSetup = new DatabaseSetup();
        databaseSetup.mockDatabase();
    }
}
