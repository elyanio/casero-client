package caribehostal.caseroclient.view.tray

import android.content.Context
import android.view.View
import caribehostal.caseroclient.R
import caribehostal.caseroclient.dataaccess.DaoAction
import caribehostal.caseroclient.datamodel.ActionState
import caribehostal.caseroserver.comunication.SmsSender
import com.airbnb.epoxy.OnModelLongClickListener
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import org.jetbrains.anko.vibrator

/**
 * @author rainermf
 */
class TrayCardLongClickListener(val callbacks: AdapterCallbacks) : OnModelLongClickListener<MessageModel_, MessageHolder> {

    override fun onLongClick(model: MessageModel_, parentView: MessageHolder, clickedView: View, position: Int): Boolean {
        if (model.state() == ActionState.PENDING) {
            val context = clickedView.context
            with(context) {
                vibrator.vibrate(50L)
                alert {
                    items(kotlin.collections.listOf("Eliminar", "Reenviar"), {
                        when (it) {
                            0 -> {
                                callbacks.onDeleteAction(model.actionId())
                            }
                            1 -> resendAction(context, model.actionId())
                        }
                        dismiss()
                    })
                }.show()
            }
        }
        return true
    }

    private val smsSender = SmsSender()

    private fun resendAction(context: Context, actionId: Int) {
        smsSender.sendSms(DaoAction().getAction(actionId))
        context.toast(R.string.message_action_resent)
    }
}