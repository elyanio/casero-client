package caribehostal.caseroclient.view.tray

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import caribehostal.caseroclient.R
import caribehostal.caseroclient.dataaccess.DaoAction
import kotlinx.android.synthetic.main.activity_tray.*
import org.jetbrains.anko.ctx

class TrayActivity : AppCompatActivity() {

    val trayPresenter: TrayPresenter by lazy {
        TrayPresenter(ctx)
    }

    val dao = DaoAction()

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_tray_all -> {
                trayPresenter.fill{ dao.allActions }
                true
            }
            R.id.nav_tray_pending -> {
                trayPresenter.fill{ dao.pendingActions }
                true
            }
            R.id.nav_tray_processed -> {
                trayPresenter.fill { dao.confirmedActions }
                true
            }
            else -> {
                false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tray)

        content.addView(trayPresenter.view)

        trayPresenter.fill { dao.allActions }

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
