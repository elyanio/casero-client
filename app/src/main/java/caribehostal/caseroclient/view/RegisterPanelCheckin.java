package caribehostal.caseroclient.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.threeten.bp.LocalDate;

import butterknife.BindView;
import butterknife.ButterKnife;
import caribehostal.caseroclient.R;

/**
 * Created by Fernando on 24/08/2017.
 */

public class RegisterPanelCheckin extends RelativeLayout implements RegisterPanel{
    private final Context context;
    @BindView(R.id.text_register_check_panel)
    TextView text;
    @BindView(R.id.datePicker_check)
    DatePicker datePicker;
    LocalDate localDate;


    public RegisterPanelCheckin(Context context, LocalDate localDate) {
        super(context);
        this.context = context;
        bindXML();
        ButterKnife.bind(this);
        this.text.setText("Fecha de llegada");
        datePicker.init(localDate.getYear(),localDate.getMonthValue() - 1,localDate.getDayOfMonth(), new MyOnDateChangedListener());
        this.localDate = localDate;
    }

    private void bindXML() {
        String infladorServicio = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater asioInflador = (LayoutInflater) getContext().getSystemService(infladorServicio);
        asioInflador.inflate(R.layout.register_panel_date, this, true);
    }

    public LocalDate getLocalDate(){
        return localDate;
    }

    public TextView getText() {
        return text;
    }

    public void setText(TextView text) {
        this.text = text;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public void outPanel() {
        RegisterClient context = (RegisterClient) this.context;
        context.outCheckinPanelAction();
    }


    class MyOnDateChangedListener implements DatePicker.OnDateChangedListener{

        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            localDate = LocalDate.of(year,monthOfYear + 1,dayOfMonth);
        }
    }
}
