package caribehostal.caseroclient.datamodel

import caribehostal.caseroclient.dataaccess.DaoActionClient

fun Action.toSmsString(): String {
//    codigopeticion#tipoaction#pasapoes#checkin#checkout
    val daoActionClient = DaoActionClient()
    var sms: String  =  "${this.id}#${ActionTypeConverter().convertToPersisted(this.actionType)}"
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