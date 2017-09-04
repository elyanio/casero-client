package caribehostal.caseroclient.view.tray

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import caribehostal.caseroclient.R
import caribehostal.caseroclient.controllers.RegisterClientController
import caribehostal.caseroclient.dataaccess.DaoAction
import caribehostal.caseroclient.view.about.AboutActivity
import kotlinx.android.synthetic.main.activity_tray.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity

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
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun onResume() {
        trayPresenter.fill { dao.allActions }
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.list_tray_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_add_action -> startActivity<RegisterClientController>()
            R.id.action_about -> startActivity<AboutActivity>()
        }
        return super.onOptionsItemSelected(item)
    }
}
