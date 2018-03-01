package caribehostal.caseroclient.view.about;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import caribehostal.caseroclient.R;
import caribehostal.caseroclient.dataaccess.DaoDevelop;
import caribehostal.caseroclient.datamodel.Develop;

import static caribehostal.caseroclient.util.PhoneUtils.dial;
import static caribehostal.caseroclient.util.PhoneUtils.sendSms;

public class DevelopActivity extends AppCompatActivity {

    @BindView(R.id.develop_text1_phone1) TextView text1;
    @BindView(R.id.develop_text1_phone2) TextView text2;
    @BindView(R.id.develop_text1_phone3) TextView text3;
    @BindView(R.id.develop_text1_phone4) TextView text4;
    @BindView(R.id.develop_text1_phone5) TextView text5;

    private List<Develop> developsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.develop_activity);
        DaoDevelop daoDevelop = new DaoDevelop();
        developsList = daoDevelop.getAllDevelops().toList();
        ButterKnife.bind(this);
        text1.setText(developsList.get(0).getName());
        text2.setText(developsList.get(1).getName());
        text3.setText(developsList.get(2).getName());
        text4.setText(developsList.get(3).getName());
        text5.setText(developsList.get(4).getName());
    }

    @OnClick(R.id.develop_image1_phone1)
    public void onPhone1Click() {
        dial(this, developsList.get(0).getCell());
    }

    @OnClick(R.id.develop_image1_phone2)
    public void onPhone2Click() {
        dial(this, developsList.get(1).getCell());
    }

    @OnClick(R.id.develop_image1_phone3)
    public void onPhone3Click() {
        dial(this, developsList.get(2).getCell());
    }

    @OnClick(R.id.develop_image1_phone4)
    public void onPhone4Click() {
        dial(this, developsList.get(3).getCell());
    }

    @OnClick(R.id.develop_image1_phone5)
    public void onPhone5Click() {
        dial(this, developsList.get(4).getCell());
    }

    @OnClick(R.id.develop_image2_phone1)
    public void onSms1Click() {
        sendSms(this, developsList.get(0).getCell());
    }

    @OnClick(R.id.develop_image2_phone2)
    public void onSms2Click() {
        sendSms(this, developsList.get(1).getCell());
    }

    @OnClick(R.id.develop_image2_phone3)
    public void onSms3Click() {
        sendSms(this, developsList.get(2).getCell());
    }

    @OnClick(R.id.develop_image2_phone4)
    public void onSms4Click() {
        sendSms(this, developsList.get(3).getCell());
    }

    @OnClick(R.id.develop_image2_phone5)
    public void onSms5Click() {
        sendSms(this, developsList.get(4).getCell());
    }

    @OnClick(R.id.develop_button1)
    public void outClick() {
        finish();
    }
}
