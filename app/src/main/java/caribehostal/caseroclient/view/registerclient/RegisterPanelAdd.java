package caribehostal.caseroclient.view.registerclient;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import caribehostal.caseroclient.R;
import caribehostal.caseroclient.controllers.RegisterClientController;

/**
 * Created by Fernando on 24/08/2017.
 */

public class RegisterPanelAdd extends FrameLayout implements RegisterPanel{
    private final ClientRecyclerAdapter adapter;
    private final RegisterClientController context;
    @BindView(R.id.list_client)
    RecyclerView recyclerView;
    @BindView(R.id.button_add_client)
    FloatingActionButton btAddCLient;

    public RegisterPanelAdd(Context context) {
        super(context);
        this.context = (RegisterClientController)context;
        bindXML();
        ButterKnife.bind(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new ClientRecyclerAdapter(this.context.getClients(), context);
        recyclerView.setAdapter(adapter);

        btAddCLient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
    }

    private void showAddDialog() {
        context.showAddClientDialogAction();
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
