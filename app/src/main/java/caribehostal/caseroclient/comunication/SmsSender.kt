package caribehostal.caseroserver.comunication

import android.telephony.SmsManager
import caribehostal.caseroclient.NUMBER_CELL
import caribehostal.caseroclient.datamodel.Action
import caribehostal.caseroclient.datamodel.toSmsString
import caribehostal.caseroclient.view.registerserver.RegisterData

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

    fun sendSms(registerData: RegisterData) {
        var body = "2#" + registerData.fullName + "#" + registerData.id + "#" + registerData.user + "#" + registerData.password + "#" + registerData.adress + "#" + registerData.reference
        val message = smsManager.divideMessage(body)
        smsManager.sendMultipartTextMessage(NUMBER_CELL, null, message, null, null)
    }
}