package caribehostal.caseroclient.view.registerserver

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import caribehostal.caseroclient.MainActivity
import caribehostal.caseroclient.R
import caribehostal.caseroclient.settings.Settings
import caribehostal.caseroclient.settings.Settings.setSendPetition


class StageRegisterServer : AppCompatActivity() {
    var registerData: RegisterData = RegisterData()

    private var containerScene: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_server_stage)
        containerScene = findViewById(R.id.rs_stage_container) as LinearLayout
        containerScene!!.addView(RegisterServerScene1(this))
    }


    // aciones de las scene

    fun cancelAllScene() {
        outRegister()
    }

    fun backScene1() {
        containerScene!!.removeAllViews()
        containerScene!!.addView(RegisterServerScene1(this))
    }

    fun goScene2() {
        containerScene!!.removeAllViews()
        containerScene!!.addView(RegisterServerScene2(this, registerData))
    }

    fun backScene2() {
        goScene2()
    }

    fun goScene3(data: RegisterData) {
        registerData.fullName = data.fullName
        registerData.id = data.id
        registerData.adress = data.adress
        registerData.reference = data.reference
        containerScene!!.removeAllViews()
        containerScene!!.addView(RegisterServerScene3(this, registerData))
    }

    fun backScene3() {
        containerScene!!.removeAllViews()
        containerScene!!.addView(RegisterServerScene3(this, registerData))
    }

    fun goScene4(data: RegisterData) {
        registerData.user = data.user
        registerData.password = data.password
        containerScene!!.removeAllViews()
        containerScene!!.addView(RegisterServerScene4(this, this.registerData))
    }

    fun backScene4() {
        containerScene!!.removeAllViews()
        containerScene!!.addView(RegisterServerScene4(this, registerData))
    }

    fun goScene5() {
        sendREgisterSMS()
        saveConfigurations()
        containerScene!!.removeAllViews()
        containerScene!!.addView(RegisterServerScene5(this, registerData))
    }

    private fun saveConfigurations() {
        setSendPetition(true)
    }

    private fun sendREgisterSMS() {

    }

    fun outRegister() {
        val mainIntent = Intent().setClass(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    fun cancelScene1() {
        AlertDialog.Builder(this).setTitle("¿Seguro se encuentra registrado en el sistema?")
                .setPositiveButton("Aceptar") { dialog, which -> acept() }.setNegativeButton("Cancelar") { dialog, which -> }.show()
    }

    fun acept(){
        Settings.setSendPetition(true)
        Settings.setActivation(true)
        val mainIntent = Intent().setClass(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

}
