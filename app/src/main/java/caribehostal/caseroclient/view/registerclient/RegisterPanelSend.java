package caribehostal.caseroclient.view.registerclient;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.threeten.bp.format.DateTimeFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;
import caribehostal.caseroclient.R;
import caribehostal.caseroclient.controllers.RegisterClientController;

/**
 * Created by Fernando on 27/08/2017.
 */

public class RegisterPanelSend extends LinearLayout implements RegisterPanel {

    private final RegisterClientController context;
    @BindView(R.id.text_message)
    TextView text;
    @BindView(R.id.button_send_client)
    FloatingActionButton btSendAction;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("y-MM-dd");

    public RegisterPanelSend(Context context) {
        super(context);
        this.context = (RegisterClientController) context;
        bindXML();
        ButterKnife.bind(this);
        btSendAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAction();
            }
        });
        updateText();
    }

    private void sendAction() {
        context.sendAction();
    }

    private void bindXML() {
        String infladorServicio = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater asioInflador = (LayoutInflater) getContext().getSystemService(infladorServicio);
        asioInflador.inflate(R.layout.register_panel_ok, this, true);
    }

    private String makeText() {
        if (context.getClients().isEmpty()) {
            text.setTextColor(getResources().getColor(R.color.colorerror));
            btSendAction.setVisibility(INVISIBLE);
            return "La petición no contiene pasaportes";
        }
        if (context.getRegisterPanelDate().getCheckin().compareTo(context.getRegisterPanelDate().getCheckout()) >= 0) {
            text.setTextColor(getResources().getColor(R.color.colorerror));
            btSendAction.setVisibility(INVISIBLE);
            return "La fecha de llegada no puede ser superior o igual a la fecha de salida";
        }
        btSendAction.setVisibility(VISIBLE);
        text.setTextColor(getResources().getColor(R.color.correcttext));
        return correctText();
    }

    private String correctText() {
        String text = "Usted enviará una petición con" +
                "\n" + "Pasaportes:" +
                "\n";
        for (int i = 0; i < context.getClients().size(); i++) {
            if (i == context.getClients().size() - 1) {
                text = text + context.getClients().get(i).getPassport();
            } else {
                text = text + context.getClients().get(i).getPassport() + "\n";
            }
        }
        text = text +
                "\n" + "fecha de entrada: " + context.getRegisterPanelDate().getCheckin().format(formatter) +
                "\n" + "fecha de salida: " + context.getRegisterPanelDate().getCheckout().format(formatter);
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
