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

public class RegisterPanelCheckout extends RelativeLayout implements RegisterPanel{
    private final RegisterClient context;
    @BindView(R.id.text_register_check_panel)
    TextView text;
    @BindView(R.id.datePicker_check)
    DatePicker datePicker;

    public RegisterPanelCheckout(Context context) {
        super(context);
        this.context = (RegisterClient)context;
        bindXML();
        ButterKnife.bind(this);
        this.text.setText("Fecha de salida");
        datePicker.init(this.context.getCheckout().getYear(),this.context.getCheckout().getMonthValue() - 1,this.context.getCheckout().getDayOfMonth(), new MyOnDateChangedListener());
    }

    private void bindXML() {
        String infladorServicio = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater asioInflador = (LayoutInflater) getContext().getSystemService(infladorServicio);
        asioInflador.inflate(R.layout.register_panel_date, this, true);
    }

    @Override
    public void outPanel() {
        context.outCheckoutPanelAction();
    }


    class MyOnDateChangedListener implements DatePicker.OnDateChangedListener{

        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            context.setCheckout(LocalDate.of(year,monthOfYear + 1,dayOfMonth));
        }
    }
}
