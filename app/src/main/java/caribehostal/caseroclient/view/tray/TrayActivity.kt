package caribehostal.caseroclient.view.tray

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import caribehostal.caseroclient.R
import caribehostal.caseroclient.SHOULD_UPDATE
import caribehostal.caseroclient.controllers.RegisterClientController
import caribehostal.caseroclient.dataaccess.DaoAction
import caribehostal.caseroclient.datamodel.ActionState.FINISH
import caribehostal.caseroclient.datamodel.ActionState.PENDING
import caribehostal.caseroclient.datamodel.FullAction
import caribehostal.caseroclient.view.about.AboutActivity
import kotlinx.android.synthetic.main.activity_tray.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

const val CREATE_ACTION_REQUEST = 1
const val NEW_ACTION_ID = "newActionId"
const val NIL = -1

class TrayActivity : AppCompatActivity(), AdapterCallbacks {

    val controller = TrayController(this)
    val dao = DaoAction()
    lateinit var allActions: List<FullAction>
    val pendingActions = { allActions.filter { it.state == PENDING } }
    val checkedActions = { allActions.filter { it.state == FINISH } }

    private val onNavigationItemSelectedListener = OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_tray_pending, R.id.nav_tray_checked -> {
                updateController(item.itemId)
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

        val restore = lastCustomNonConfigurationInstance
        if(restore != null && restore is TrayRestore) {
            allActions = restore.actions
        } else {
            allActions = dao.loadAllActions().sortedByDescending { it.updateTime }
        }

        content.layoutManager = LinearLayoutManager(this)
        content.adapter = controller.adapter
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        controller.adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    content.layoutManager.scrollToPosition(0)
                }
            }
        })
    }

    fun getActions(itemId: Int): () -> List<FullAction> = when (itemId) {
        R.id.nav_tray_pending -> pendingActions
        R.id.nav_tray_checked -> checkedActions
        else -> {
            { emptyList() }
        }
    }

    private fun updateController() {
        updateController(navigation.selectedItemId)
    }

    private fun updateController(itemId: Int) {
        controller.setData(getActions(itemId).invoke())
    }

    override fun onResume() {
        if (SHOULD_UPDATE.compareAndSet(true, false)) {
            allActions = dao.loadAllActions()
        }
        updateController()
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == CREATE_ACTION_REQUEST && resultCode == RESULT_OK) {
            val actionId = data.getIntExtra(NEW_ACTION_ID, NIL)
            if (actionId != NIL) {
                val action = dao.getFullAction(actionId)
                allActions = listOf(action) + allActions
            }
            navigation.selectedItemId = R.id.nav_tray_pending
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.list_tray_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_action -> startActivityForResult<RegisterClientController>(CREATE_ACTION_REQUEST)
            R.id.action_about -> startActivity<AboutActivity>()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDeleteAction(actionId: Int) {
        dao.deleteAction(actionId)
        allActions = allActions.filterNot { it.id == actionId }
        updateController()
    }

    override fun onActionRead(actionId: Int) {
        dao.updateUnread(actionId, false)
        allActions = allActions.map {
            if (it.id == actionId) it.copy(unread = false) else it
        }
        updateController()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Permissions.onRequestPermissionsResult(ctx, requestCode, permissions, grantResults)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any  {
        return TrayRestore(actions = allActions)
    }
}

class TrayRestore(val actions: List<FullAction>)
