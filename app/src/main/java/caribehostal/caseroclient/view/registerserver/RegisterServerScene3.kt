package caribehostal.caseroclient.view.registerserver

import android.content.Context
import android.support.design.widget.TextInputLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import caribehostal.caseroclient.R
import caribehostal.caseroclient.view.registerclient.RegisterServerScene


class RegisterServerScene3(context: Context?, registerData: RegisterData) : LinearLayout(context), RegisterServerScene {
    private var layoutUser: TextInputLayout? = null
    private var layoutPassword: TextInputLayout? = null
    private var next: Button? = null
    private var back: Button? = null
    private var user: EditText? = null
    private var password: EditText? = null
    private val ERRORUSER: String = "Su usuario no debe ser vacio"
    private val ERRORPASSWORD: String = "Su contraseña no debe ser vacia"

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
        asioInflador.inflate(R.layout.register_server_scene_3, this, true)
    }

    private fun getControlXML() {
        layoutUser = findViewById(R.id.rs_scene_3_input_user) as TextInputLayout
        layoutPassword = findViewById(R.id.rs_scene_3_input_password) as TextInputLayout
        user = findViewById(R.id.rs_scene_3_text_user) as EditText
        password = findViewById(R.id.rs_scene_3_text_password) as EditText
        back = findViewById(R.id.rs_scene_3_bt_back) as Button
        next = findViewById(R.id.rs_scene_3_bt_next) as Button
    }

    private fun configControl(registerData: RegisterData) {
        user!!.setText(registerData.user)
        password!!.setText(registerData.password)
    }

    private fun event() {
        next!!.setOnClickListener(View.OnClickListener {
            nextAction()
        })
        back!!.setOnClickListener(View.OnClickListener {
            backAction()
        })
    }

    private fun getRegisterData(): RegisterData {
        val registerData = RegisterData()
        registerData.user = user?.text.toString()
        registerData.password = password?.text.toString()
        return registerData
    }

    private fun nextAction() {
        val registerData = getRegisterData()
        cleanFieldError()
        if (validate(registerData)) stage.goScene4(registerData)
    }

    private fun backAction() {
        stage.backScene2()
    }

    private fun validate(registerData: RegisterData): Boolean {
        var result: Boolean = true
        if (registerData.user.isEmpty()) {
            layoutUser?.isErrorEnabled = true
            layoutUser?.error = ERRORUSER
            result = false
        }
        if (registerData.password.isEmpty()) {
            layoutPassword?.isErrorEnabled = true
            layoutPassword?.error = ERRORPASSWORD
            result = false
        }
        return result
    }

    private fun cleanFieldError() {
        layoutUser?.isErrorEnabled = false
        layoutPassword?.isErrorEnabled = false
    }

    override fun getNumberScene(): Int {
        return 3
    }
}
