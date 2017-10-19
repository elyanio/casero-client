package caribehostal.caseroclient.view.registerclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.squareup.timessquare.CalendarPickerView;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import caribehostal.caseroclient.R;
import caribehostal.caseroclient.controllers.RegisterClientController;

import static caribehostal.caseroclient.util.DateUtils.toDate;
import static caribehostal.caseroclient.util.DateUtils.toLocalDate;
import static com.squareup.timessquare.CalendarPickerView.SelectionMode.RANGE;

/**
 * Created by Fernando on 24/08/2017.
 */

public class RegisterPanelDate extends RelativeLayout implements RegisterPanel {
    private final RegisterClientController context;

    @BindView(R.id.datePicker_check)
    CalendarPickerView datePicker;

    public RegisterPanelDate(Context context) {
        super(context);
        this.context = (RegisterClientController) context;
        bindXML();
        ButterKnife.bind(this);
        initDatePicker();
    }

    private void bindXML() {
        String infladorServicio = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater asioInflador = (LayoutInflater) getContext().getSystemService(infladorServicio);
        asioInflador.inflate(R.layout.register_panel_date, this, true);
    }

    private void initDatePicker() {
        Date dateInit = toDate(LocalDate.now().minusMonths(1));
        Date dateFin = toDate(LocalDate.now().plusYears(1));
        ArrayList<Date> range = new ArrayList<>();
        range.add(toDate(LocalDate.now()));
        range.add(toDate(LocalDate.now()));
        datePicker.init(dateInit, dateFin).inMode(RANGE)
                .withSelectedDates(range);
    }

    public LocalDate getCheckin() {
        try {
            return toLocalDate(datePicker.getSelectedDates().get(0));
        }catch (IndexOutOfBoundsException e){
            return LocalDate.now();
        }
    }

    public LocalDate getCheckout() {
        try {
            List<Date> selectedDates = datePicker.getSelectedDates();
            return toLocalDate(selectedDates.get(selectedDates.size() - 1));
        }catch (IndexOutOfBoundsException e){
            return LocalDate.now();
        }
    }

    @Override
    public void outPanel() {
        context.outCheckinPanelAction();
    }

}
