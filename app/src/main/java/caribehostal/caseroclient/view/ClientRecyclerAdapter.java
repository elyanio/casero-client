 package caribehostal.caseroclient.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import caribehostal.caseroclient.R;
import caribehostal.caseroclient.dataaccess.DaoClient;
import caribehostal.caseroclient.datamodel.Client;

/**
 * Created by Fernando on 24/08/2017.
 */

public class ClientRecyclerAdapter extends RecyclerView.Adapter<ClientRecyclerAdapter.MyViewHolder> {

    private List<Client> dataSet;
    private Context context;

    public ClientRecyclerAdapter(List<Client> data, Context context) {
        this.dataSet = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_client, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        holder.passport.setText(dataSet.get(listPosition).getPassport());
        holder.buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(listPosition);
            }
        });
    }

    private void removeItem(int listPosition) {
        DaoClient daoClient = new DaoClient();
        daoClient.remove(dataSet.get(listPosition));
        dataSet.remove(listPosition);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView passport;
        ImageView buttonRemove;

        public MyViewHolder(View itemView) {
            super(itemView);
            passport = (TextView) itemView.findViewById(R.id.item_client_passport);
            buttonRemove = (ImageView) itemView.findViewById(R.id.item_owner_imageButton);
        }
    }
}
