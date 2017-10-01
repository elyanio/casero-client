package caribehostal.caseroclient.settings

import android.content.SharedPreferences
import android.preference.PreferenceManager
import caribehostal.caseroclient.*

/**
 * @author rainermf
 */
object Settings {

    fun prefs(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(CaseroClientApplication.instance())
    @JvmStatic fun isActivated(): Boolean = prefs().getBoolean(PREFE_APK_ACTIVATED,false)
    @JvmStatic fun setActivation(vari: Boolean) = prefs().edit().putBoolean(PREFE_APK_ACTIVATED,vari).commit()
    @JvmStatic fun isPayMethodAcepted(): Boolean = prefs().getBoolean(PREFE_PAY_AND_VISION_ACEPTED,false)
    @JvmStatic fun setPayMethodAcepted(vari: Boolean) = prefs().edit().putBoolean(PREFE_PAY_AND_VISION_ACEPTED,vari).commit()
    @JvmStatic fun isSendRegister(): Boolean = prefs().getBoolean(PREFE_SEND_SMS_REGISTER,false)
    @JvmStatic fun setSendRegister(vari: Boolean) = prefs().edit().putBoolean(PREFE_SEND_SMS_REGISTER,vari).commit()
    @JvmStatic fun getPrice(): String = prefs().getString(PREFE_PRICE_CURRENT,"-1")
    @JvmStatic fun setPrice(vari: String) = prefs().edit().putString(PREFE_PRICE_CURRENT,vari).commit()
}