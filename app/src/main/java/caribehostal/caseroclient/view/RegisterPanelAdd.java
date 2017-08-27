package caribehostal.caseroclient.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import caribehostal.caseroclient.R;
import caribehostal.caseroclient.datamodel.Client;

/**
 * Created by Fernando on 24/08/2017.
 */

public class RegisterPanelAdd extends RelativeLayout implements RegisterPanel{
    private final ClientRecyclerAdapter adapter;
    @BindView(R.id.list_client)
    RecyclerView recyclerView;

    private List<Client> clients;

    public RegisterPanelAdd(Context context, List<Client> clients) {
        super(context);
        bindXML();
        ButterKnife.bind(this);
        this.clients = clients;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new ClientRecyclerAdapter(this.clients, context);
        recyclerView.setAdapter(adapter);
    }

    private void bindXML() {
        String infladorServicio = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater asioInflador = (LayoutInflater) getContext().getSystemService(infladorServicio);
        asioInflador.inflate(R.layout.register_panel_client, this, true);
    }

    @Override
    public void outPanel() {
        RegisterClient registerClient = (RegisterClient) getContext();
        registerClient.outClientPanelAction();
    }

    public void updateList(){
        adapter.notifyDataSetChanged();
    }

}
