package caribehostal.caseroclient.settings

import android.content.SharedPreferences
import android.preference.PreferenceManager
import caribehostal.caseroclient.*

/**
 * @author rainermf
 */
object Settings {

    fun prefs(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(CaseroClientApplication.instance())
    @JvmStatic fun isApkActivated(): Boolean = prefs().getBoolean(PREFE_APK_ACTIVATED,false)
    @JvmStatic fun setApkActivation(vari: Boolean) = prefs().edit().putBoolean(PREFE_APK_ACTIVATED,vari).commit()
    @JvmStatic fun isPayAndVisionAcepted(): Boolean = prefs().getBoolean(PREFE_TERMINUS_ACEPTED,false)
    @JvmStatic fun setPayAndVisionAcepted(vari: Boolean) = prefs().edit().putBoolean(PREFE_TERMINUS_ACEPTED,vari).commit()
    @JvmStatic fun isRegisterServerSend(): Boolean = prefs().getBoolean(PREFE_SEND_SMS_REGISTER,false)
    @JvmStatic fun setRegisterServerSend(vari: Boolean) = prefs().edit().putBoolean(PREFE_SEND_SMS_REGISTER,vari).commit()
    @JvmStatic fun getCurrentPrice(): String = prefs().getString(PREFE_PRICE_CURRENT,"-1")
    @JvmStatic fun setCurrentPrice(vari: String) = prefs().edit().putString(PREFE_PRICE_CURRENT,vari).commit()
}