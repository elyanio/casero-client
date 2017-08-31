package caribehostal.caseroclient.view.tray

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import caribehostal.caseroclient.R
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import kotlinx.android.synthetic.main.activity_tray.*
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

class TrayActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tray)

        val context = ComponentContext(this)
        val view = TrayCard.create(context)
                .arg1(arrayOf("90122633288", "90122636024"))
                .arg2(LocalTime.now())
                .arg3(LocalDate.now())
                .arg4(LocalDate.now().plusDays(4))
                .build()

        content.addView(LithoView.create(context, view))
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

}
