package caribehostal.caseroclient.comunication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import caribehostal.caseroclient.NUMBER_CELL
import caribehostal.caseroclient.SHOULD_UPDATE
import caribehostal.caseroclient.SPLIT_SYMBOL
import caribehostal.caseroclient.dataaccess.DaoAction
import caribehostal.caseroclient.dataaccess.DaoActionClient
import caribehostal.caseroclient.datamodel.Action
import caribehostal.caseroclient.datamodel.ActionState
import caribehostal.caseroclient.datamodel.LocalDateTimeConverter
import caribehostal.caseroclient.notifications.NotificationBar
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class SmsReceiver : BroadcastReceiver() {
    private val formatter = DateTimeFormatter.ofPattern("y-MM-dd")
    private val ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
    private val TITLE_ACTION_NOTIFICATION = "Petición completada"

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent!!.action
        if (action == ACTION_SMS_RECEIVED) {
            val msgs = getMessagesFromIntent(intent)
            var numberSender: String = ""
            var messageBody: String = ""

            for (msg in msgs) {
                if(msg != null) {
                    numberSender = msg.originatingAddress
                    messageBody += msg.messageBody
                }
            }

            if (NUMBER_CELL == numberSender.substring(numberSender.length - 8)) {
                processResponse(messageBody.trim(), context)
            }
        }
    }

    fun getMessagesFromIntent(intent: Intent): Array<SmsMessage?> {
        val messages = intent.getSerializableExtra("pdus") as Array<Any>
        val pduObjs = arrayOfNulls<ByteArray>(messages.size)

        for (i in messages.indices) {
            pduObjs[i] = messages[i] as ByteArray
        }

        val pdus = arrayOfNulls<ByteArray>(pduObjs.size)
        val pduCount = pdus.size
        val msgs = arrayOfNulls<SmsMessage>(pduCount)

        for (i in 0..pduCount - 1) {
            pdus[i] = pduObjs[i]
            msgs[i] = SmsMessage.createFromPdu(pdus[i])
        }
        return msgs
    }

    private fun processResponse(messageBody: String, context: Context?) {
        val daoActionClient = DaoActionClient()
        val fields = messageBody.split(SPLIT_SYMBOL)
        var action = getAction(fields)
        action?.let {
            action = getUpdateAction(fields)
            SHOULD_UPDATE.set(true)
            val confirmCodes = getConfirmCodes(fields)
            val actionClient = daoActionClient.getActionClient(action)
            if (confirmCodes.size == actionClient.size) {
                var index = 0
                for (code in confirmCodes) {
                    daoActionClient.updateConfirmationCode(actionClient.get(index), code)
                    index++
                }
                notifyAction(action!!, context)
            }
        }
    }

    private fun notifyAction(action: Action, context: Context?) {
        val notificationBar = NotificationBar()
        notificationBar.createNotification(context, action.id, TITLE_ACTION_NOTIFICATION, "", makeBigTextNotification(action))
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
        } catch (e: NumberFormatException) {
            return null
        }

    }

    private fun makeBigTextNotification(action: Action): String {
        val daoActionClient = DaoActionClient()
        val clients = daoActionClient.getClients(action).toList()
        var text = "Petición con" +
                "\n" + "Pasaportes:" +
                "\n"
        for (i in 0..clients.size - 1) {
            if (i == clients.size - 1) {
                text += clients[i].passport
            } else {
                text = text + clients[i].passport + "\n"
            }
        }
        text = text +
                "\n" + "fecha de entrada: " + action.checkIn.format(formatter) +
                "\n" + "fecha de salida: " + action.checkOut.format(formatter)
        return text
    }
}