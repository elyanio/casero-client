package caribehostal.caseroclient.comunication

import android.telephony.SmsManager
import caribehostal.caseroclient.NUMBER_CELL
import caribehostal.caseroclient.datamodel.Action
import caribehostal.caseroclient.datamodel.toSmsString

/**
* @author asio
*/
class SmsSender {

    val smsManager = SmsManager.getDefault()

    fun enviarMensaje(numero: String, mensaje: String) {
        smsManager.sendTextMessage(numero, null, mensaje, null, null)
    }

    fun sendSms(action: Action) {
        enviarMensaje(NUMBER_CELL, action.toSmsString())
    }
}