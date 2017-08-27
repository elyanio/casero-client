package caribehostal.caseroclient.view.registerclient;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.threeten.bp.format.DateTimeFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;
import caribehostal.caseroclient.R;
import caribehostal.caseroclient.controllers.RegisterClientController;
import caribehostal.caseroclient.datamodel.Client;

/**
 * Created by Fernando on 27/08/2017.
 */

public class RegisterPanelSend extends LinearLayout implements RegisterPanel {

    private final RegisterClientController context;
    @BindView(R.id.text_message)
    TextView text;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("y-MM-dd");

    public RegisterPanelSend(Context context) {
        super(context);
        this.context = (RegisterClientController) context;
        bindXML();
        ButterKnife.bind(this);
        updateText();
    }

    private void bindXML() {
        String infladorServicio = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater asioInflador = (LayoutInflater) getContext().getSystemService(infladorServicio);
        asioInflador.inflate(R.layout.register_panel_ok, this, true);
    }

    private String makeText() {
        if (context.getClient().isEmpty()) {
            text.setTextColor(getResources().getColor(R.color.colorerror));
            return "La petición no contiene pasaportes";
        }
        if (context.getCheckin().compareTo(context.getCheckout()) >= 0) {
            text.setTextColor(getResources().getColor(R.color.colorerror));
            return "La fecha de llegada no puede ser superior o igual a la fecha de salida";
        }
        text.setTextColor(getResources().getColor(R.color.correcttext));
        return correctText();
    }

    private String correctText() {
        String text = "Usted enviará una petición con" +
                "\n" + "Pasaportes:" +
                "\n";
        for (Client client : context.getClient()) {
            text = text + client.getPassport();
            Log.e("sd", "d");
        }
        text = text +
                "\n" + "fecha de entrada: " + context.getCheckin().format(formatter) +
                "\n" + "fecha de salida: " + context.getCheckout().format(formatter);
        return text;
    }

    public void updateText() {
        text.setText(makeText());
    }

    @Override
    public void outPanel() {
        context.outSendPanelAction();
    }
}
