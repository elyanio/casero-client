package caribehostal.caseroserver.comunication

import android.telephony.SmsManager

/**
* @author asio
*/
class SmsSender {

    val smsManager = SmsManager.getDefault()

    fun enviarMensaje(numero: String, mensaje: String) {
        smsManager.sendTextMessage(numero, null, mensaje, null, null)
    }

    fun enviarMensaje(numbers: List<String>, mensaje: String) {
        for (number in numbers) {
            enviarMensaje(number, mensaje)
        }
    }
}