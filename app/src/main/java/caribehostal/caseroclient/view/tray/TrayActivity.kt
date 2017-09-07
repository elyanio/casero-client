package caribehostal.caseroclient.view.tray

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import caribehostal.caseroclient.R
import caribehostal.caseroclient.controllers.RegisterClientController
import caribehostal.caseroclient.dataaccess.DaoAction
import caribehostal.caseroclient.datamodel.Action
import caribehostal.caseroclient.view.about.AboutActivity
import kotlinx.android.synthetic.main.activity_tray.*
import org.jetbrains.anko.startActivity

class TrayActivity : AppCompatActivity(), AdapterCallbacks {

    val controller = TrayController(this)
    val dao = DaoAction()
    var updateAction: () -> List<Action> = { dao.allActions }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_tray_all -> {
                updateController({ dao.allActions })
                true
            }
            R.id.nav_tray_pending -> {
                updateController({ dao.pendingActions })
                true
            }
            R.id.nav_tray_processed -> {
                updateController({ dao.confirmedActions })
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

        content.layoutManager = LinearLayoutManager(this)
        content.adapter = controller.adapter
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        updateController()
    }

    private fun updateController(updateActionParam: () -> List<Action>) {
        updateAction = updateActionParam
        updateController()
    }

    private fun updateController() {
        controller.setData(updateAction.invoke())
    }

    override fun onResume() {
        updateController()
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.list_tray_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_action -> startActivity<RegisterClientController>()
            R.id.action_about -> startActivity<AboutActivity>()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDeleteAction(actionId: Int) {
        dao.deleteAction(actionId)
        updateController()
    }

    override fun onActionRead(actionId: Int) {
        dao.updateUnread(actionId, false)
        updateController()
    }
}
