package caribehostal.caseroclient.view.tray

import android.Manifest.permission.SEND_SMS
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat.checkSelfPermission
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
        val context = clickedView.context
        val actions = actions(context, model)
        val actionNames = actions.keys.toList()
        with(context) {
            vibrator.vibrate(50L)
            alert {
                items(actionNames, {
                    actions[actionNames[it]]?.invoke()
                    dismiss()
                })
            }.show()
        }

        return true
    }

    private fun actions(context: Context, model: MessageModel_): Map<String, () -> Unit> {
        var actions = mapOf("Eliminar" to { callbacks.onDeleteAction(model.actionId()) })
        if (model.state() == ActionState.PENDING) {
            actions += mapOf("Reenviar" to { resendAction(context, model.actionId()) })
        }
        return actions
    }

    private val smsSender = SmsSender()

    private fun resendAction(context: Context, actionId: Int) {
        if (checkSelfPermission(context, SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            smsSender.sendSms(DaoAction().getAction(actionId))
            context.toast(R.string.message_action_resent)
        }
    }
}