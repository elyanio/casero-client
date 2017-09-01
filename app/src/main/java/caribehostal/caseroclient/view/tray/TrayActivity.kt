package caribehostal.caseroclient.view.tray

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.OrientationHelper.VERTICAL
import caribehostal.caseroclient.R
import caribehostal.caseroclient.dataaccess.DaoAction
import caribehostal.caseroclient.dataaccess.DaoActionClient
import caribehostal.caseroclient.dataaccess.getClientInfo
import caribehostal.caseroclient.datamodel.Action
import caribehostal.caseroclient.util.MEDIUM_DATE
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import com.facebook.litho.widget.ComponentRenderInfo
import com.facebook.litho.widget.LinearLayoutInfo
import com.facebook.litho.widget.Recycler
import com.facebook.litho.widget.RecyclerBinder
import kotlinx.android.synthetic.main.activity_tray.*
import org.threeten.bp.LocalDate

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

        val recyclerBinder = RecyclerBinder.Builder()
                .layoutInfo(LinearLayoutInfo(this, VERTICAL, false))
                .build(context)

        val tray = Recycler.create(context)
                .binder(recyclerBinder)
                .build()
        addContent(recyclerBinder, context)

        content.addView(LithoView.create(context, tray))
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun addContent(recyclerBinder: RecyclerBinder, context: ComponentContext) {
        val actionsByDate: Map<LocalDate, List<Action>> = DaoAction().allAction.groupBy { it.sendTime.toLocalDate() }
        val daoActionClient = DaoActionClient()
        var i = 0
        for (date in actionsByDate.keys) {
            recyclerBinder.insertItemAt(i,
                    ComponentRenderInfo.create()
                            .component(DayTitle.create(context)
                                    .title(date.plusDays(i.toLong()).format(MEDIUM_DATE))
                                    .build())
                            .build())
            i++
            actionsByDate[date]?.forEach { action ->
                val clientInfo = daoActionClient.getClientInfo(action).toTypedArray()
                recyclerBinder.insertItemAt(i,
                        ComponentRenderInfo.create()
                                .component(TrayCard.create(context)
                                        .clientInfo(clientInfo)
                                        .arrivalTime(action.sendTime.toLocalTime())
                                        .checkInDate(action.checkIn)
                                        .checkOutDate(action.checkOut)
                                        .build())
                                .build()
                )
                i++
            }
        }
    }
}
