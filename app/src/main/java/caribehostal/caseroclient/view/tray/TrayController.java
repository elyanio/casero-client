package caribehostal.caseroclient.view.tray;

import com.airbnb.epoxy.TypedEpoxyController;

import java.util.List;

import caribehostal.caseroclient.datamodel.FullAction;

import static caribehostal.caseroclient.datamodel.ActionState.PENDING;

/**
 * @author rainermf
 */
public class TrayController extends TypedEpoxyController<List<FullAction>> {

    AdapterCallbacks callbacks;

    public TrayController(AdapterCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    protected void buildModels(List<FullAction> data) {
        for (final FullAction action : data) {
            new MessageModel_()
                    .id(action.getId())
                    .actionId(action.getId())
                    .checkInDate(action.getCheckIn())
                    .checkOutDate(action.getCheckOut())
                    .passports(action.getPassports())
                    .confirmationCodes(action.getResponseCodes())
                    .time((action.getState() == PENDING) ? action.getSendTime() : action.getResponseTime())
                    .state(action.getState())
                    .isUnread(action.getUnread())
                    .longClickListener(new TrayCardLongClickListener(callbacks))
                    .clickListener(new TrayCardClickListener(callbacks))
                    .addTo(this);
        }
    }
}
