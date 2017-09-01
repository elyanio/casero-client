package caribehostal.caseroclient.datamodel

import caribehostal.caseroclient.dataaccess.DaoActionClient

fun Action.toSmsString(): String {
//    codigopeticion#tipoaction#pasapoes#checkin#checkout
    val daoActionClient = DaoActionClient()
    val sms: String  =  "${this.id}#${ActionTypeConverter().convertToPersisted(this.actionType)}"
    for (client in daoActionClient.getClients(this)) {
        "${sms}#${client.passport}"
    }
    "${sms}#${this.checkIn}"
    "${sms}#${this.checkOut}"
    return sms

}