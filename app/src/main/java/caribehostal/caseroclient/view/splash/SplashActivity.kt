package caribehostal.caseroclient.view.splash

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.Window
import caribehostal.caseroclient.MainActivity
import caribehostal.caseroclient.R
import caribehostal.caseroclient.settings.Settings
import caribehostal.caseroclient.view.about.TerminusActivity
import caribehostal.caseroclient.view.registerserver.StageRegisterServer
import caribehostal.caseroclient.view.tray.REQUEST_WRITE_ES
import java.util.*

const val SPLASH_SCREEN_DELAY: Long = 800

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.splash_screen)

        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED) {
            val task = object : TimerTask() {
                override fun run() {
                    init()
                }
            }
            Timer().schedule(task, SPLASH_SCREEN_DELAY)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE), REQUEST_WRITE_ES)
        }
    }

    private fun init() {
        val mainIntent: Intent
        if (Settings.isApkActivated()) {
            if (Settings.isPayAndVisionAcepted()) {
                mainIntent = Intent().setClass(this, MainActivity::class.java)
            } else {
                mainIntent = Intent().setClass(this, TerminusActivity::class.java)
            }
        } else {
            if (Settings.isRegisterServerSend()) {
                mainIntent = Intent().setClass(this, MainActivity::class.java)
            } else {
                mainIntent = Intent().setClass(this, StageRegisterServer::class.java)
            }

        }
        startActivity(mainIntent)
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_WRITE_ES) {
            if (grantResults[0] == PERMISSION_GRANTED) {
                init()
            } else {
                AlertDialog.Builder(this)
                        .setMessage(getString(R.string.msg_close_app_due_permissions))
                        .setPositiveButton("Aceptar", { _, _ -> })
                        .setOnDismissListener({
                            ActivityCompat.finishAffinity(this@SplashActivity)
                        })
                        .show()
            }
        }
    }

}