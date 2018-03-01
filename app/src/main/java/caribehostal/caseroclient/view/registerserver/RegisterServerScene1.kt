package caribehostal.caseroclient.view.registerserver

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import caribehostal.caseroclient.R


class RegisterServerScene1(context: Context?) : LinearLayout(context), RegisterServerScene {

    private var cancel: Button? = null
    private var next: Button? = null

    private var stage: StageRegisterServer

    init {
        bindXML()
        stage = context as StageRegisterServer
    }

    private fun bindXML() {
        val infladorServicio = Context.LAYOUT_INFLATER_SERVICE
        val asioInflador = context.getSystemService(infladorServicio) as LayoutInflater
        asioInflador.inflate(R.layout.register_server_scene_1, this, true)
        cancel = findViewById(R.id.scene_1_bt_cancel) as Button
        next = findViewById(R.id.scene_1_bt_next) as Button
        cancel!!.setOnClickListener(View.OnClickListener {
            cancelAction()
        })
        next!!.setOnClickListener(View.OnClickListener {
            nextAction()
        })
    }

    private fun nextAction() {
        stage.goScene2()
    }

    private fun cancelAction() {
        stage.cancelScene1()
    }

    override fun getNumberScene(): Int {
        return 1
    }
}
