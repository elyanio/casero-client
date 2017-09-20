package caribehostal.caseroclient.view.registerserver

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import butterknife.BindView

import caribehostal.caseroclient.R
import android.widget.EditText
import caribehostal.caseroclient.MainActivity


class StageRegisterServer : AppCompatActivity() {
    var registerDate: RegisterData = RegisterData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_server_stage)
        var containerScene = findViewById(R.id.rs_stage_container) as LinearLayout
        containerScene.addView(RegisterServerScene1(this))
    }


    // aciones de las ecenas

    fun cancelAllScene() {
        val intent = Intent().setClass(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
