package caribehostal.caseroclient.view.registerserver

class RegisterData {
    var fullName: String = ""
    var id: String = ""
    var user: String = ""
    var password: String = ""
    var adress: String = ""
    var reference: String = ""

    fun isFullNameEmpty(): Boolean {
        return fullName.equals("")
    }
    fun isIdEmpty(): Boolean {
        return id.equals("")
    }
    fun isFUserEmpty(): Boolean {
        return user.equals("")
    }
    fun isPaswordEmpty(): Boolean {
        return password.equals("")
    }
    fun isAdressEmpty(): Boolean {
        return adress.equals("")
    }
    fun isReferencieEmpty(): Boolean {
        return reference.equals("")
    }
}
