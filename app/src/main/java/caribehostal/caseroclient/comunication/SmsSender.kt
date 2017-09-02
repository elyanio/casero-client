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

    fun enviarMensaje(numbers: List<String>, mensaje: String) {
        for (number in numbers) {
            enviarMensaje(number, mensaje)
        }
    }

    fun sendSms(action: Action) {
        val smsSender = SmsSender()
        smsSender.enviarMensaje(numberServer, action.toSmsString())
    }
}