package caribehostal.appcasero.comunication

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Telephony.Sms.Intents.getMessagesFromIntent
import caribehostal.caseroclient.dataaccess.DaoAction
import caribehostal.caseroclient.dataaccess.DaoActionClient
import caribehostal.caseroclient.datamodel.Action
import caribehostal.caseroclient.datamodel.ActionClient
import caribehostal.caseroclient.datamodel.ActionState
import caribehostal.caseroclient.datamodel.LocalDateTimeConverter
import caribehostal.caseroserver.comunication.SmsSender
import org.threeten.bp.LocalDateTime

class SmsReceiver : BroadcastReceiver() {
    private val ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"

    @TargetApi(Build.VERSION_CODES.KITKAT)
    override fun onReceive(context: Context?, intent: Intent?) {

        val action = intent!!.action
        if (action == ACTION_SMS_RECEIVED) {
            val msgs = getMessagesFromIntent(intent)
            var numberSender: String = ""
            var messageBody: String = ""
            if (msgs != null) {
                for (msg in msgs) {
                    numberSender = msg.originatingAddress
                    messageBody += msg.messageBody
                }
            }
            val smsSender = SmsSender()
            if(smsSender.numberServer == numberSender){
                processResponse(messageBody)
            }
        }
    }

    private fun processResponse(messageBody: String) {
//        val fields = messageBody.split("#")
//        var action = getAction(fields)
//        action?.let {
//            action = getUpdateAction(fields)
//            val confirmCodes = getConfirmCodes(fields)
//            val daoActionClient = DaoActionClient()
//            var index = 0
//            for (client in daoActionClient.getClients(action)) {
//                daoActionClient.updateConfirmationCode(ac))
//                index++
//            }
//        }
    }

    private fun getConfirmCodes(fields: List<String>): List<String> {
        var index = 1
        val confirmCodes = ArrayList<String>()
        while (index < fields.size - 1) {
            confirmCodes.add(fields.get(index))
            index++
        }
        return confirmCodes
    }

    private fun getUpdateAction(fields: List<String>): Action {
        val id = fields.get(0).toInt()
        val dateProceced = LocalDateTimeConverter()
                .convertToMapped(LocalDateTime::class.java, fields.get(fields.size - 1))
        val daoAction = DaoAction()
        daoAction.updateResponseTime(id, dateProceced)
        daoAction.updateState(id, ActionState.FINISH)
        daoAction.updateUnread(id, true)
        return daoAction.getAction(id)
    }

    private fun getAction(fields: List<String>): Action? {
        try {
            val id = fields.get(0).toInt()
            val daoAction = DaoAction()
            return daoAction.getAction(id)
        }catch (e: NumberFormatException){
            return null
        }

    }
}