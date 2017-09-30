package caribehostal.caseroclient.view.registerserver

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import caribehostal.caseroclient.MainActivity
import caribehostal.caseroclient.PREFE_PRICE
import caribehostal.caseroclient.R
import caribehostal.caseroclient.settings.Settings
import caribehostal.caseroclient.view.about.ActivatedMenssage
import caribehostal.caseroclient.view.registerclient.RegisterServerScene


class StageRegisterServer : AppCompatActivity() {
    var registerData: RegisterData = RegisterData()

    private var containerScene: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_server_stage)
        containerScene = findViewById(R.id.rs_stage_container) as LinearLayout
        val restore: Restore? = lastCustomNonConfigurationInstance as Restore?
        if(restore != null){
            registerData = restore.registerData
            when (restore.sceneNumber) {
                1 -> containerScene!!.addView(RegisterServerScene1(this))
                2 -> containerScene!!.addView(RegisterServerScene2(this,registerData))
                3 -> containerScene!!.addView(RegisterServerScene3(this,registerData))
                4 -> containerScene!!.addView(RegisterServerScene4(this,registerData))
                5 -> containerScene!!.addView(RegisterServerScene5(this,registerData))
                else -> containerScene!!.addView(RegisterServerScene1(this))
            }
        }
        containerScene!!.addView(RegisterServerScene1(this))

    }


    // aciones de las scene

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
        Settings.setSendRegister(true)
        containerScene!!.removeAllViews()
        containerScene!!.addView(RegisterServerScene5(this, registerData))
    }

    private fun sendREgisterSMS() {

    }

    fun cancelScene1() {
        AlertDialog.Builder(this).setTitle("¿Seguro se encuentra registrado en el sistema?")
                .setPositiveButton("Aceptar") { dialog, which -> acept() }.setNegativeButton("Cancelar") { dialog, which -> }.show()
    }

    fun acept() {
        Settings.setActivation(true)
        Settings.setSendRegister(true)
        val mainIntent = Intent().setClass(this, ActivatedMenssage::class.java)
        mainIntent.putExtra(PREFE_PRICE,Settings.getPrice())
        startActivity(mainIntent)
        finish()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        val scene = containerScene!!.getChildAt(0) as RegisterServerScene
        return Restore(registerData, scene.getNumberScene())
    }

    inner class Restore(data: RegisterData, scene: Int) {
        val registerData: RegisterData = data
        val sceneNumber: Int = scene
    }

    fun finishRegister() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
