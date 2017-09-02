package caribehostal.caseroclient.view.tray

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.util.DiffUtil.Callback
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
import com.facebook.litho.widget.RecyclerBinder
import com.facebook.litho.widget.RecyclerBinderUpdateCallback
import com.facebook.litho.widget.RecyclerBinderUpdateCallback.ComponentRenderer
import org.threeten.bp.LocalDate

/**
 * @author rainermf
 */
class TrayPresenter(ctx: Context) {

    val context: ComponentContext = ComponentContext(ctx)

    val recyclerBinder: RecyclerBinder = RecyclerBinder.Builder()
            .layoutInfo(LinearLayoutInfo(ctx, OrientationHelper.VERTICAL, false))
            .build(context)

    val tray: Component<Tray> = Tray.create(context)
            .recyclerBinder(recyclerBinder)
            .build()

    val view: LithoView = LithoView.create(context, tray)

    val daoActionClient = DaoActionClient()

    var currentData: List<TrayData> = emptyList()

    fun fill(actionsToShow: List<Action>) {
        val newData = createData(actionsToShow)
        onNewData(newData)
    }

    fun createData(actionsToShow: List<Action>): List<TrayData> {
        val actionsByDate: Map<LocalDate, List<Action>> = actionsToShow
                .groupBy { it.sendTime.toLocalDate() }

        return actionsByDate.flatMap { (date, actions) ->
            listOf<TrayData>(DateHeader(date.format(MEDIUM_DATE))) + actions.map { action ->
                Message(
                        action = action,
                        clientInfo = daoActionClient.getClientInfo(action).toTypedArray()
                )
            }
        }
    }

    private val componentRenderer: ComponentRenderer<TrayData> = ComponentRenderer { data, idx ->
        ComponentRenderInfo.create()
                .component(data.createComponent(context))
                .build()
    }

    fun onNewData(newData: List<TrayData>) {
        val diffResult = DiffUtil.calculateDiff(DataDiffCallback(currentData, newData))
        val callback = RecyclerBinderUpdateCallback.acquire(
                currentData.size,
                newData,
                componentRenderer,
                recyclerBinder)

        diffResult.dispatchUpdatesTo(callback)
        callback.applyChangeset()
        RecyclerBinderUpdateCallback.release(callback)
        currentData = newData
    }

    class DataDiffCallback(val oldData: List<TrayData>, val newData: List<TrayData>) : Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldData[oldItemPosition]
            val newItem = newData[newItemPosition]
            if (oldItem.javaClass != newItem.javaClass) return false
            if (oldItem is Message && newItem is Message) {
                return oldItem.action.id == newItem.action.id
            }
            return oldItem == newItem
        }

        override fun getOldListSize(): Int {
            return oldData.size
        }

        override fun getNewListSize(): Int {
            return newData.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition] == newData[newItemPosition]
        }

    }
}