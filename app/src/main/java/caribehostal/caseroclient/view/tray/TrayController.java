package caribehostal.caseroclient.view.tray;

import android.support.annotation.NonNull;

import com.airbnb.epoxy.TypedEpoxyController;

import java.util.Collections;
import java.util.List;

import caribehostal.caseroclient.dataaccess.DaoActionClient;
import caribehostal.caseroclient.datamodel.Action;
import caribehostal.caseroclient.datamodel.ActionClient;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;

import static caribehostal.caseroclient.datamodel.ActionState.FINISH;
import static caribehostal.caseroclient.datamodel.ActionState.PENDING;

/**
 * @author rainermf
 */

public class TrayController extends TypedEpoxyController<List<Action>> {

    AdapterCallbacks callbacks;
    DaoActionClient dao = new DaoActionClient();

    public TrayController(AdapterCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    protected void buildModels(List<Action> data) {
        for (final Action action : data) {
            final List<ActionClient> clientInfo = dao.getActionClient(action);
            final List<String> passports = CollectionsKt.map(clientInfo, actionToPassport);
            final List<String> confirmationCodes = (action.getActioState() == FINISH) ?
                    CollectionsKt.map(clientInfo, actionToConfirmationCode) : Collections.<String>emptyList();
            new MessageModel_()
                    .id(action.getId())
                    .actionId(action.getId())
                    .checkInDate(action.getCheckIn())
                    .checkOutDate(action.getCheckOut())
                    .passports(passports)
                    .confirmationCodes(confirmationCodes)
                    .time((action.getActioState() == PENDING) ? action.getSendTime() : action.getResponseTime())
                    .state(action.getActioState())
                    .isUnread(action.isUnread())
                    .longClickListener(new TrayCardLongClickListener(callbacks))
                    .clickListener(new TrayCardClickListener(callbacks))
                    .addTo(this);
        }
    }

    @NonNull
    private Function1<ActionClient, String> actionToPassport = new Function1<ActionClient, String>() {
        @Override
        public String invoke(ActionClient actionClient) {
            return actionClient.getClient().getPassport();
        }
    };

    @NonNull
    private Function1<ActionClient, String> actionToConfirmationCode = new Function1<ActionClient, String>() {
        @Override
        public String invoke(ActionClient actionClient) {
            return actionClient.getResponseCode();
        }
    };
}
