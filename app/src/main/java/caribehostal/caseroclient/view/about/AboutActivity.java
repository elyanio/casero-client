package caribehostal.caseroclient.view.about;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import caribehostal.caseroclient.R;

import static caribehostal.caseroclient.util.PhoneUtils.dial;
import static caribehostal.caseroclient.util.PhoneUtils.sendSms;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.text_phone1_activar) TextView phone1;
    @BindView(R.id.text_phone2_activar) TextView phone2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.call_phone1_activar)
    public void onPhone1Click() {
        dial(this, phone1.getText().toString());
    }

    @OnClick(R.id.call_phone2_activar)
    public void onPhone2Click() {
        dial(this, phone2.getText().toString());
    }

    @OnClick(R.id.send_sms_phone1_activar)
    public void onSms1Click() {
        sendSms(this, phone1.getText().toString());
    }

    @OnClick(R.id.send_sms_phone2_activar)
    public void onSms2Click() {
        sendSms(this, phone2.getText().toString());
    }
}
