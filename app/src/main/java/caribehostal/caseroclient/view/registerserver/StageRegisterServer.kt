package caribehostal.caseroclient.view.registerserver

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import caribehostal.caseroclient.MainActivity
import caribehostal.caseroclient.R
import caribehostal.caseroclient.settings.Settings
import caribehostal.caseroclient.view.about.TermsActivity
import caribehostal.caseroserver.comunication.SmsSender


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

//        processSmsRegisterServerOK("2#25#12#qw#23#as#34#zx#45#er#56#df",this)
//        processSmsRegisterServerFail("3#1#2#3#4#5#6",this)
    }




//    private fun processSmsRegisterServerOK(messageBody: String, context: Context?) {
//        val daoDevelop = DaoDevelop()
//        val fields = messageBody.split(SPLIT_SYMBOL)
//        Settings.setCurrentPrice(fields.get(1))
//        var index = 2
//        daoDevelop.removeAllDevelop(daoDevelop.allDevelops.toList())
//        while (index < fields.size) {
//            daoDevelop.upsertDevelop(Develop().setCell(fields.get(index)).setName(fields.get(index + 1)))
//            index += 2
//        }
//        Settings.setApkActivation(true)
//        notifyRegiserServerOk(context)
//    }
//
//    private fun notifyRegiserServerOk(context: Context?) {
//        val notificationBar = NotificationBar()
//        notificationBar.createNotification(context, -1, "¡Enhorabuena!", "", "Ya puede comenzar a usar nuestros servicios", TermsActivity::class.java)
//    }
//    private fun processSmsRegisterServerFail(messageBody: String, context: Context?) {
//        Settings.setRegisterServerSend(false)
//        Settings.setApkActivation(false)
//        Settings.setTermsAcepted(false)
//        val notificationBar = NotificationBar()
//        notificationBar.createNotification(context, -1, "Error en registrarse", "", createSmsRegisterFail(messageBody), StageRegisterServer::class.java)
//    }
//
//    private fun createSmsRegisterFail(messageBody: String): String {
//        val fields = messageBody.split(SPLIT_SYMBOL)
//        var body = "Sus datos incorrectos son:" + "\n"
//        var index: Int = 1
//        while (index < fields.size) {
//            when (fields.get(index)) {
//            //nombre
//                "1" -> {
//                    body = body + "Nombre Completo" + "\n"
//                }
//            //id
//                "2" -> {
//                    body = body + "Carnet de identidad" + "\n"
//                }
//            //user
//                "3" -> {
//                    body = body + "Usuario" + "\n"
//                }
//            //passowrd
//                "4" -> {
//                    body = body + "Contraseña" + "\n"
//                }
//            //adress
//                "5" -> {
//                    body = body + "Dirección" + "\n"
//                }
//            //referencie
//                "6" -> {
//                    body = body + "Punto de referencia" + "\n"
//                }
//            }
//            index++
//        }
//        return body
//    }





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
        Settings.setRegisterServerSend(true)
        containerScene!!.removeAllViews()
        containerScene!!.addView(RegisterServerScene5(this, registerData))
    }

    private fun sendREgisterSMS() {
        val smsSender = SmsSender()
        smsSender.sendSms(registerData)
    }

    fun cancelScene1() {
        AlertDialog.Builder(this).setTitle("¿Seguro se encuentra registrado en el sistema?")
                .setPositiveButton("Aceptar") { dialog, which -> acept() }.setNegativeButton("Cancelar") { dialog, which -> }.show()
    }

    fun acept() {
        Settings.setApkActivation(true)
        Settings.setRegisterServerSend(true)
        startActivity(Intent().setClass(this, TermsActivity::class.java))
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
