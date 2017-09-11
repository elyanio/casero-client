package caribehostal.caseroclient.view.tray

import android.content.Context
import android.content.pm.PackageManager
import org.jetbrains.anko.toast

/**
 * @author rainermf
 */

const val REQUEST_WRITE_ES = 123
const val REQUEST_RESEND_ACTION_SMS = 234
const val REQUEST_SEND_ACTION_SMS = 235

object Permissions {
    fun onRequestPermissionsResult(context: Context, requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        with(context) {
            when (requestCode) {
                REQUEST_RESEND_ACTION_SMS, REQUEST_SEND_ACTION_SMS -> {
                    if (grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                        toast("Reenvíe la acción ahora, por favor.")
                    } else {
                        toast("La acción no pudo ser enviada. Active los permisos de envío de SMS.")
                    }
                }
            }
        }
    }
}