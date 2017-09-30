package caribehostal.caseroclient.settings

import android.content.SharedPreferences
import android.preference.PreferenceManager
import caribehostal.caseroclient.*

/**
 * @author rainermf
 */
object Settings {

    fun prefs(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(CaseroClientApplication.instance())
    @JvmStatic fun isActivated(): Boolean = prefs().getBoolean(PREFE_ACTIVATED,false)
    @JvmStatic fun setActivation(vari: Boolean) = prefs().edit().putBoolean(PREFE_ACTIVATED,vari).commit()
    @JvmStatic fun isPayMethodAcepted(): Boolean = prefs().getBoolean(PREFE_PAYACEPTED,false)
    @JvmStatic fun setPayMethodAcepted(vari: Boolean) = prefs().edit().putBoolean(PREFE_PAYACEPTED,vari).commit()
    @JvmStatic fun isSendRegister(): Boolean = prefs().getBoolean(PREFE_SENDEGISTER,false)
    @JvmStatic fun setSendRegister(vari: Boolean) = prefs().edit().putBoolean(PREFE_SENDEGISTER,vari).commit()
    @JvmStatic fun getPrice(): String = prefs().getString(PREFE_PRICE,"-1")
    @JvmStatic fun setPrice(vari: String) = prefs().edit().putString(PREFE_PRICE,vari).commit()
}