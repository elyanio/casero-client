package caribehostal.caseroclient.view.registerserver

import android.content.Context
import android.support.design.widget.TextInputLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import caribehostal.caseroclient.R
import caribehostal.caseroserver.util.ValideString


class RegisterServerScene2(context: Context?, registerData: RegisterData) : LinearLayout(context), RegisterServerScene {
    private var next: Button? = null
    private var back: Button? = null
    private var layoutName: TextInputLayout? = null
    private var layoutCarnet: TextInputLayout? = null
    private var layoutAdress: TextInputLayout? = null
    private var layoutReferencie: TextInputLayout? = null
    private var name: EditText? = null
    private var carnet: EditText? = null
    private var adress: EditText? = null
    private var referencie: EditText? = null
    private val ERRORFULLNAME: String = "Su nombre no debe ser vacio"
    private val ERRORID: String = "Carnet no válido"
    private val ERRORADRESS: String = "Su dirección no debe ser vacia"
    private val ERRORREFERENCIE: String = "Su punto de referencia no debe ser vacia"

    private var stage: StageRegisterServer

    init {
        stage = context as StageRegisterServer
        bindXML()
        getControlXML()
        configControl(registerData)
        event()
    }

    private fun bindXML() {
        val infladorServicio = Context.LAYOUT_INFLATER_SERVICE
        val asioInflador = context.getSystemService(infladorServicio) as LayoutInflater
        asioInflador.inflate(R.layout.register_server_scene_2, this, true)
    }

    private fun getControlXML() {
        layoutName = findViewById(R.id.rs_scene_2_input_name) as TextInputLayout
        layoutCarnet = findViewById(R.id.rs_scene_2_input_carnet) as TextInputLayout
        layoutAdress = findViewById(R.id.rs_scene_2_input_address) as TextInputLayout
        layoutReferencie = findViewById(R.id.rs_scene_2_input_address_description) as TextInputLayout

        name = findViewById(R.id.rs_scene_2_text_name) as EditText
        carnet = findViewById(R.id.rs_scene_2_text_carnet) as EditText
        adress = findViewById(R.id.rs_scene_2_text_address) as EditText
        referencie = findViewById(R.id.rs_scene_2_text_description) as EditText

        next = findViewById(R.id.rs_scene_2_bt_next) as Button
        back = findViewById(R.id.rs_scene_2_bt_back) as Button
    }

    private fun configControl(registerData: RegisterData) {
        name!!.setText(registerData.fullName)
        carnet!!.setText(registerData.id)
        adress!!.setText(registerData.adress)
        referencie!!.setText(registerData.reference)
    }

    private fun event() {
        back!!.setOnClickListener(View.OnClickListener {
            backAction()
        })
        next!!.setOnClickListener(View.OnClickListener {
            nextAction()
        })
    }

    private fun nextAction() {
        val registerData = getRegisterData()
        cleanFieldError()
        if (validate(registerData)) stage.goScene3(registerData)
    }

    private fun backAction() {
        stage.backScene1()
    }

    private fun getRegisterData(): RegisterData {
        val registerData = RegisterData()
        registerData.fullName = name?.text.toString()
        registerData.id = carnet?.text.toString()
        registerData.adress = adress?.text.toString()
        registerData.reference = referencie?.text.toString()
        return registerData
    }

    private fun validate(registerData: RegisterData): Boolean {
        var result: Boolean = true
        if (registerData.fullName.isEmpty()) {
            layoutName?.isErrorEnabled = true
            layoutName?.error = ERRORFULLNAME
            result = false
        }
        if (registerData.id.isEmpty() || !ValideString.isCarnet(registerData.id)) {
            layoutCarnet?.isErrorEnabled = true
            layoutCarnet?.error = ERRORID
            result = false
        }
        if (registerData.adress.isEmpty()) {
            layoutAdress?.isErrorEnabled = true
            layoutAdress?.error = ERRORADRESS
            result = false
        }
        if (registerData.reference.isEmpty()) {
            layoutReferencie?.isErrorEnabled = true
            layoutReferencie?.error = ERRORREFERENCIE
            result = false
        }
        return result
    }

    private fun cleanFieldError() {
        layoutName?.isErrorEnabled = false
        layoutCarnet?.isErrorEnabled = false
        layoutAdress?.isErrorEnabled = false
        layoutReferencie?.isErrorEnabled = false
    }

    override fun getNumberScene(): Int {
        return 2
    }
}
