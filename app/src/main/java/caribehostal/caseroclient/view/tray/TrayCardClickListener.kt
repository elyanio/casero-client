package caribehostal.caseroclient.view.tray

import android.view.View
import com.airbnb.epoxy.OnModelClickListener

/**
 * @author rainermf
 */
class TrayCardClickListener(val callbacks: AdapterCallbacks) : OnModelClickListener<MessageModel_, MessageHolder> {

    override fun onClick(model: MessageModel_, parentView: MessageHolder, clickedView: View, position: Int) {
        if(model.isUnread()) {
            callbacks.onActionRead(model.actionId())
        }
    }
}