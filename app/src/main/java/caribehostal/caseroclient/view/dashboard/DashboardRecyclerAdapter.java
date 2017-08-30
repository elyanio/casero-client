package caribehostal.caseroclient.view.dashboard;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import caribehostal.caseroclient.R;
import caribehostal.caseroclient.dataaccess.DaoActionClient;
import caribehostal.caseroclient.datamodel.Action;
import caribehostal.caseroclient.datamodel.Client;
import io.requery.query.Result;

/**
 * Created by Fernando on 24/08/2017.
 */

public class DashboardRecyclerAdapter extends RecyclerView.Adapter<DashboardRecyclerAdapter.MyViewHolder> {
    private DateTimeFormatter formatterHour = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private List<Action> dataSet;

    public DashboardRecyclerAdapter(List<Action> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dashboard, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        Log.e("","llegue");
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        holder.numberPetition.setText(dataSet.get(listPosition).getId() + "");
        holder.hour.setText(dataSet.get(listPosition).getSendTime().format(formatterHour));
        holder.checkin.setText(dataSet.get(listPosition).getCheckIn().format(formatterDate));
        holder.checkout.setText(dataSet.get(listPosition).getCheckOut().format(formatterDate));
        DaoActionClient daoActionClient = new DaoActionClient();
        Result<Client> resultClient = daoActionClient.getClient(dataSet.get(listPosition));
        ArrayList<Client> clients = new ArrayList<>();
        resultClient.collect(clients);
        String stringPasaport = "";
        for (int i = 0; i < clients.size(); i++) {
            if (i == clients.size() - 1) {
                stringPasaport = stringPasaport + clients.get(i).getPassport();
            } else {
                stringPasaport = stringPasaport + clients.get(i).getPassport() + "\n";
            }
        }
        holder.passport.setText(stringPasaport);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView numberPetition;
        TextView hour;
        TextView passport;
        TextView checkin;
        TextView checkout;

        public MyViewHolder(View itemView) {
            super(itemView);
            numberPetition = (TextView) itemView.findViewById(R.id.text_number_petition);
            hour = (TextView) itemView.findViewById(R.id.text_hour);
            passport = (TextView) itemView.findViewById(R.id.text_pasaport);
            checkin = (TextView) itemView.findViewById(R.id.text_checkin);
            checkout = (TextView) itemView.findViewById(R.id.text_checkout);
        }
    }
}
