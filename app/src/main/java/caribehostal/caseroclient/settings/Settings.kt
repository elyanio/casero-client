package caribehostal.caseroclient.settings

import android.content.SharedPreferences
import android.preference.PreferenceManager
import caribehostal.caseroclient.CaseroClientApplication
import caribehostal.caseroclient.PREFE_ACTIVATED
import caribehostal.caseroclient.PREFE_SEND_PETITION

/**
 * @author rainermf
 */
object Settings {

    fun prefs(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(CaseroClientApplication.instance())
    @JvmStatic fun isActivated(): Boolean = prefs().getBoolean(PREFE_ACTIVATED,false)
    @JvmStatic fun setActivation(vari: Boolean) = prefs().edit().putBoolean(PREFE_ACTIVATED,vari).commit()
    @JvmStatic fun isSendPetition(): Boolean = prefs().getBoolean(PREFE_SEND_PETITION,false)
    @JvmStatic fun setSendPetition(vari: Boolean) = prefs().edit().putBoolean(PREFE_SEND_PETITION,vari).commit()
}