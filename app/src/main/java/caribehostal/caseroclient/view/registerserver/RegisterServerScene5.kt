package caribehostal.caseroclient.view.registerserver

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import caribehostal.caseroclient.R


class RegisterServerScene5(context: Context?, registerData: RegisterData) : LinearLayout(context) {
    private var entendido: Button? = null

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
        entendido!!.setOnClickListener(View.OnClickListener {
            entendidoAction()
        })
    }

    private fun getControlXML() {
        entendido = findViewById(R.id.bt_scene_5_entendido) as Button
    }

    private fun bindXML() {
        val infladorServicio = Context.LAYOUT_INFLATER_SERVICE
        val asioInflador = context.getSystemService(infladorServicio) as LayoutInflater
        asioInflador.inflate(R.layout.register_server_scene_5, this, true)
    }

    private fun entendidoAction() {
        stage.outRegister()
    }


}
