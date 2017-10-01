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
import caribehostal.caseroclient.dataaccess.DaoDevelop
import caribehostal.caseroclient.datamodel.*
import caribehostal.caseroclient.notifications.NotificationBar
import caribehostal.caseroclient.settings.Settings
import caribehostal.caseroclient.view.about.TerminusActivity
import caribehostal.caseroclient.view.registerserver.StageRegisterServer
import caribehostal.caseroclient.view.tray.TrayActivity
import org.threeten.bp.LocalDateTime

class SmsReceiver : BroadcastReceiver() {
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
        val fields = messageBody.split(SPLIT_SYMBOL)
        when(fields.get(0)){
            "1" -> processSmsRegisterClient(messageBody, context)
            "2" -> processSmsRegisterServerOK(messageBody, context)
            "3" -> processSmsRegisterServerFail(messageBody, context)
        }
    }

    private fun processSmsRegisterClient(messageBody: String, context: Context?){
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
        notificationBar.createNotification(context, action.id, TITLE_ACTION_NOTIFICATION, "", action.makeBigTextNotification(), TrayActivity::class.java)
    }

    private fun getConfirmCodes(fields: List<String>): List<String> {
        var index = 2
        val confirmCodes = ArrayList<String>()
        while (index < fields.size - 1) {
            confirmCodes.add(fields.get(index))
            index++
        }
        return confirmCodes
    }

    private fun getUpdateAction(fields: List<String>): Action {
        val id = fields.get(1).toInt()
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
            val id = fields.get(1).toInt()
            val daoAction = DaoAction()
            return daoAction.getAction(id)
        } catch (e: NumberFormatException) {
            return null
        }

    }

    private fun processSmsRegisterServerOK(messageBody: String, context: Context?) {
        val daoDevelop = DaoDevelop()
        val fields = messageBody.split(SPLIT_SYMBOL)
        Settings.setCurrentPrice(fields.get(1))
        var index = 2
        daoDevelop.removeAllDevelop(daoDevelop.allDevelops.toList())
        while (index < fields.size) {
            daoDevelop.upsertDevelop(Develop().setCell(fields.get(index)).setName(fields.get(index)))
            index++
        }
        Settings.setApkActivation(true)
        notifyRegiserServerOk(context)
    }

    private fun notifyRegiserServerOk(context: Context?) {
        val notificationBar = NotificationBar()
        notificationBar.createNotification(context, -1, "¡Enhorabuena!", "", "Ya puede comenzar a usar nuestros servicios", TerminusActivity::class.java)
    }

    private fun processSmsRegisterServerFail(messageBody: String, context: Context?) {
        Settings.setRegisterServerSend(false)
        Settings.setApkActivation(false)
        Settings.setPayAndVisionAcepted(false)
        val notificationBar = NotificationBar()
        notificationBar.createNotification(context, -1, "Error en registrarse", "", createSmsRegisterFail(messageBody), StageRegisterServer::class.java)
    }

    private fun createSmsRegisterFail(messageBody: String): String{
        val fields = messageBody.split(SPLIT_SYMBOL)
        var body = "Sus datos incorrectos son:"+"\n"
        var index: Int = 1
        while (index < fields.size) {
            when(fields.get(index)){
            //nombre
                "1" -> {
                    body = body + "Nombre Completo" + "\n"
                }
            //id
                "2" -> {
                    body = body + "Carnet de identidad" + "\n"
                }
            //user
                "3" -> {
                    body = body + "Usuario" + "\n"
                }
            //passowrd
                "4" -> {
                    body = body + "Contraseña" + "\n"
                }
            //adress
                "5" -> {
                    body = body + "Dirección" + "\n"
                }
            //referencie
                "6" -> {
                    body = body + "Punto de referencia" + "\n"
                }
            }
            index++
        }
        return body
    }


}