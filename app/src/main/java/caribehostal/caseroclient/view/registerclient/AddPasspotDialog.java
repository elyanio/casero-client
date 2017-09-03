package caribehostal.caseroclient.view.registerclient;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import caribehostal.caseroclient.ConstantKt;
import caribehostal.caseroclient.R;
import caribehostal.caseroclient.controllers.RegisterClientController;

/**
 * Created by Fernando on 26/08/2017.
 */

public class AddPasspotDialog {
    private final Context context;
    private final View item;
    private AlertDialog.Builder addPassportDialog;

    public AddPasspotDialog(@NonNull final Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        item = inflater.inflate(R.layout.item_add_client, null);
        addPassportDialog = new AlertDialog.Builder(context).setTitle("Insertar pasaporte")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        acept();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setView(item);

    }

    private void acept() {
        if(!isEmptyPassport()){
            TextView textpassport = (TextView)item.findViewById(R.id.item_text_passport);
            if(!textpassport.getText().toString().contains(ConstantKt.SPLIT_SYMBOL)){
                RegisterClientController context = (RegisterClientController) this.context;
                context.addClientListAction(getPassport());
            }else{
                Toast.makeText(context,"El pasaporte no puede contener #", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(context,"Pasaporte vacio", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isEmptyPassport() {
        TextView textpassport = (TextView)item.findViewById(R.id.item_text_passport);
        return  textpassport.getText().toString().isEmpty();
    }

    public String getPassport(){
        TextView textpassport = (TextView)item.findViewById(R.id.item_text_passport);
        return textpassport.getText().toString();
    }

    public void show(){
        addPassportDialog.show();
    }
}
