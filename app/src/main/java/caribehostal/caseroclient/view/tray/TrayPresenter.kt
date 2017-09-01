package caribehostal.caseroclient.view.tray

import android.content.Context
import android.support.v7.widget.OrientationHelper
import caribehostal.caseroclient.dataaccess.DaoActionClient
import caribehostal.caseroclient.dataaccess.getClientInfo
import caribehostal.caseroclient.datamodel.Action
import caribehostal.caseroclient.util.MEDIUM_DATE
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import com.facebook.litho.widget.ComponentRenderInfo
import com.facebook.litho.widget.LinearLayoutInfo
import com.facebook.litho.widget.Recycler
import com.facebook.litho.widget.RecyclerBinder
import org.threeten.bp.LocalDate

/**
 * @author rainermf
 */
class TrayPresenter(ctx: Context) {

    val context: ComponentContext = ComponentContext(ctx)

    val recyclerBinder: RecyclerBinder = RecyclerBinder.Builder()
            .layoutInfo(LinearLayoutInfo(ctx, OrientationHelper.VERTICAL, false))
            .build(context)

    val tray: Component<Recycler> = Recycler.create(context)
            .binder(recyclerBinder)
            .build()

    val view: LithoView = LithoView.create(context, tray)

    val daoActionClient = DaoActionClient()

    fun fill(actionsToShow: List<Action>) {
        val actionsByDate: Map<LocalDate, List<Action>> = actionsToShow
                .groupBy { it.sendTime.toLocalDate() }
        var i = 0
        for (date in actionsByDate.keys.sortedDescending()) {
            recyclerBinder.insertItemAt(i, ComponentRenderInfo.create()
                    .component(DayTitle.create(context)
                            .title(date.format(MEDIUM_DATE))
                            .build())
                    .build())
            i++
            val actions: List<Action>? = actionsByDate[date]
            if (actions != null) {

            }
            actionsByDate[date]?.sortedByDescending { it.sendTime }?.forEach { action ->
                val clientInfo = daoActionClient.getClientInfo(action).toTypedArray()
                recyclerBinder.insertItemAt(i, ComponentRenderInfo.create()
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