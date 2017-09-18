package caribehostal.caseroserver.settings

import android.content.SharedPreferences
import android.preference.PreferenceManager
import caribehostal.caseroclient.CaseroClientApplication
import caribehostal.caseroclient.PREFE_ACTIVATED

/**
 * @author rainermf
 */
object Settings {

    fun prefs(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(CaseroClientApplication.instance())
    fun isActivated(): Boolean = prefs().getBoolean(PREFE_ACTIVATED,false)
    fun setActivation(vari: Boolean) = prefs().edit().putBoolean(PREFE_ACTIVATED,vari)
}