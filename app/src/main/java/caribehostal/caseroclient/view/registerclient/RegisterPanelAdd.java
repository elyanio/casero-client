package caribehostal.caseroclient.view.registerclient;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import caribehostal.caseroclient.R;
import caribehostal.caseroclient.controllers.RegisterClientController;

/**
 * Created by Fernando on 24/08/2017.
 */

public class RegisterPanelAdd extends RelativeLayout implements RegisterPanel{
    private final ClientRecyclerAdapter adapter;
    private final RegisterClientController context;
    @BindView(R.id.list_client)
    RecyclerView recyclerView;

    public RegisterPanelAdd(Context context) {
        super(context);
        this.context = (RegisterClientController)context;
        bindXML();
        ButterKnife.bind(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new ClientRecyclerAdapter(this.context.getClient(), context);
        recyclerView.setAdapter(adapter);
    }

    private void bindXML() {
        String infladorServicio = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater asioInflador = (LayoutInflater) getContext().getSystemService(infladorServicio);
        asioInflador.inflate(R.layout.register_panel_client, this, true);
    }

    @Override
    public void outPanel() {
        context.outClientPanelAction();
    }

    public void updateList(){
        adapter.notifyDataSetChanged();
    }

}
