package caribehostal.caseroclient.view.registerserver

import android.content.Context
import android.support.design.widget.TextInputLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import caribehostal.caseroclient.R


class RegisterServerScene2(context: Context?, registerData: RegisterData) : LinearLayout(context) {
    private var cancel: Button? = null
    private var next: Button? = null
//    private var name: TextInputLayout? = null
    
    private var stage: StageRegisterServer
    private var registerData: RegisterData

    init {
        this.registerData = registerData
        stage = context as StageRegisterServer
        bindXML()
        getControlXML()
        event()
    }

    private fun event() {
        cancel!!.setOnClickListener(View.OnClickListener {
            cancelAction()
        })
        next!!.setOnClickListener(View.OnClickListener {
            nextAction()
        })
    }

    private fun getControlXML() {
        cancel = findViewById(R.id.bt_scene_2_cancel) as Button
        next = findViewById(R.id.bt_scene_2_next) as Button
//        name = findViewById(R.id.input_scene_2_name) as TextInputLayout
    }

    private fun bindXML() {
        val infladorServicio = Context.LAYOUT_INFLATER_SERVICE
        val asioInflador = context.getSystemService(infladorServicio) as LayoutInflater
        asioInflador.inflate(R.layout.register_server_scene_2, this, true)
    }

    private fun nextAction() {

    }

    private fun cancelAction() {
        stage.cancelAllScene()
    }
}
