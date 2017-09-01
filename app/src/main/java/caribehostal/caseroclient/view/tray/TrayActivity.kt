package caribehostal.caseroclient.view.tray

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import caribehostal.caseroclient.R
import caribehostal.caseroclient.dataaccess.DaoAction
import kotlinx.android.synthetic.main.activity_tray.*
import org.jetbrains.anko.ctx

class TrayActivity : AppCompatActivity() {

    val presenters: List<TrayPresenter> by lazy {
        List(3) { TrayPresenter(ctx) }
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        content.removeAllViews()
        when (item.itemId) {
            R.id.nav_tray_all -> {
                content.addView(presenters[0].view)
                true
            }
            R.id.nav_tray_pending -> {
                content.addView(presenters[1].view)
                true
            }
            R.id.nav_tray_processed -> {
                content.addView(presenters[2].view)
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

        content.addView(presenters[0].view)

        val dao = DaoAction()

        presenters[0].fill(dao.allActions)
        presenters[1].fill(dao.pendingActions)
        presenters[2].fill(dao.confirmedActions)

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
