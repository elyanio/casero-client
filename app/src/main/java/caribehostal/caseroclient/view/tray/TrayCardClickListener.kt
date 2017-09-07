package caribehostal.caseroclient.view.tray

import android.view.View

/**
 * @author rainermf
 */
class TrayCardClickListener(val callbacks: AdapterCallbacks) : com.airbnb.epoxy.OnModelClickListener<MessageModel_, MessageHolder> {

    override fun onClick(model: MessageModel_, parentView: MessageHolder, clickedView: View, position: Int) {
        if(model.isUnread()) {
            callbacks.onActionRead(model.actionId())
        }
    }
}