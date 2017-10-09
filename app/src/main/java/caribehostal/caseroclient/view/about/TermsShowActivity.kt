package caribehostal.caseroclient.view.about

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import caribehostal.caseroclient.MainActivity

import caribehostal.caseroclient.R
import caribehostal.caseroclient.settings.Settings

class TermsShowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.terminus_ativity)
        var priceText = findViewById(R.id.pay_text_9) as TextView
        val intent = intent
        val price = Settings.getCurrentPrice()
        if(price.equals("-1")){
            priceText.text = "Usted debe conocer la tarifa actual si ya estaba registrado"
        }else{
            priceText.text = "La tarifa actual es ${price} pesos moneda nacional"
        }

        var entendido = findViewById(R.id.pay_method_bt) as Button
        entendido.setOnClickListener {
//            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


}
