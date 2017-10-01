package caribehostal.caseroclient.datamodel

import caribehostal.caseroclient.dataaccess.DaoActionClient
import org.threeten.bp.format.DateTimeFormatter

private val formatter = DateTimeFormatter.ofPattern("y-MM-dd")

fun Action.toSmsString(): String {
//    codigopeticion#tipoaction#pasapoes#checkin#checkout
    val daoActionClient = DaoActionClient()
    var sms: String  =  "1#${this.id}#${ActionTypeConverter().convertToPersisted(this.actionType)}"
    for (client in daoActionClient.getClients(this)) {
        sms = "$sms#${client.passport}"
//        Log.e("kkkkkkkkk   ", client.passport)
    }
    sms = "${sms}#${this.checkIn}"
//    Log.e("mmmmmmmmmmm   ", LocalDateConverter().convertToPersisted(this.checkIn))
    sms = "${sms}#${this.checkOut}"
//    Log.e("mmmmmmmmmmm   ", LocalDateConverter().convertToPersisted(this.checkOut))
    return sms
}

fun Action.makeBigTextNotification(): String {
    val daoActionClient = DaoActionClient()
    val clients = daoActionClient.getClients(this).toList()
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
            "\n" + "fecha de entrada: " + this.checkIn.format(formatter) +
            "\n" + "fecha de salida: " + this.checkOut.format(formatter)
    return text
}