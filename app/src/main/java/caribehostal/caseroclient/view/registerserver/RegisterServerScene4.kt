package caribehostal.caseroclient.view.registerserver

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import caribehostal.caseroclient.R


class RegisterServerScene4(context: Context?, registerData: RegisterData) : LinearLayout(context), RegisterServerScene {

    private var name: TextView? = null
    private var carnet: TextView? = null
    private var adress: TextView? = null
    private var referencie: TextView? = null
    private var user: TextView? = null
    private var password: TextView? = null
    private var cancel: Button? = null
    private var register: Button? = null

    private var stage: StageRegisterServer

    init {
        stage = context as StageRegisterServer
        bindXML()
        getControlXML()
        configControl(registerData)
        event()
    }

    private fun event() {
        cancel!!.setOnClickListener(View.OnClickListener {
            backAction()
        })
        register!!.setOnClickListener(View.OnClickListener {
            registerAction()
        })
    }

    private fun getControlXML() {
        name = findViewById(R.id.scene_4_text_1) as TextView
        carnet = findViewById(R.id.scene_4_text_2) as TextView
        adress = findViewById(R.id.scene_4_text_3) as TextView
        referencie = findViewById(R.id.scene_4_text_4) as TextView
        user = findViewById(R.id.scene_4_text_5) as TextView
        password = findViewById(R.id.scene_4_text_6) as TextView

        cancel = findViewById(R.id.bt_scene_4_cancel) as Button
        register = findViewById(R.id.bt_scene_4_register) as Button
    }

    private fun configControl(registerData: RegisterData) {
        name!!.text = registerData.fullName
        carnet!!.text = registerData.id
        adress!!.text = registerData.adress
        referencie!!.text = registerData.reference
        user!!.text = registerData.user
        password!!.text = registerData.password
    }

    private fun bindXML() {
        val infladorServicio = Context.LAYOUT_INFLATER_SERVICE
        val asioInflador = context.getSystemService(infladorServicio) as LayoutInflater
        asioInflador.inflate(R.layout.register_server_scene_4, this, true)
    }

    private fun registerAction() {
        stage.goScene5()
    }

    private fun backAction() {
        stage.backScene3()
    }

    override fun getNumberScene(): Int {
        return 4
    }
}
