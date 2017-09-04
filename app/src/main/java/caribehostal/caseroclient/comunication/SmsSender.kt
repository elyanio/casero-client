package caribehostal.caseroserver.comunication

import android.telephony.SmsManager
import caribehostal.caseroclient.datamodel.Action
import caribehostal.caseroclient.datamodel.toSmsString

/**
* @author asio
*/
class SmsSender {
    var numberServer = "54520426"

    val smsManager = SmsManager.getDefault()

    fun enviarMensaje(numero: String, mensaje: String) {
        smsManager.sendTextMessage(numero, null, mensaje, null, null)
    }

    fun sendSms(action: Action) {
//        enviarMensaje(numberServer, action.toSmsString())
    }
}