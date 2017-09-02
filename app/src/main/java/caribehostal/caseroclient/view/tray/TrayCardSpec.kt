package caribehostal.caseroclient.view.tray

import android.support.annotation.ColorRes
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import caribehostal.caseroclient.R
import caribehostal.caseroclient.datamodel.Action
import caribehostal.caseroclient.datamodel.ActionState
import caribehostal.caseroclient.datamodel.ClientInfo
import com.facebook.litho.*
import com.facebook.litho.annotations.*
import com.facebook.litho.widget.Card
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast

/**
 * @author rainermf
 */
@LayoutSpec
object TrayCardSpec {

    @OnCreateLayout
    @JvmStatic fun onCreateLayout(
            c: ComponentContext,
            @Prop action: Action,
            @Prop clientInfo: Array<ClientInfo>,
//            @Prop onActionRemoved: (Int) -> Unit,
            @State isSelected: Boolean
    ): ComponentLayout = Card.create(c)
            .content(TrayCardContent.create(c)
                    .action(action)
                    .clientInfo(clientInfo))
            .cornerRadiusRes(R.dimen.card_radius)
            .elevationRes(R.dimen.card_elevation)
            .cardBackgroundColorRes(if (isSelected) R.color.colorAccent else colorByState(action.actioState))
            .withLayout()
            .touchHandler(TrayCard.onTouch(c))
            .clickHandler(TrayCard.onClick(c))
            .longClickHandler(TrayCard.onLongClick(c))
            .build()

    @OnUpdateState
    @JvmStatic fun updateSelectionState(isSelected: StateValue<Boolean>) {
        isSelected.set(!isSelected.get())
    }

    @OnEvent(TouchEvent::class)
    @JvmStatic fun onTouch(
            c: ComponentContext,
            @FromEvent view: View,
            @FromEvent motionEvent: MotionEvent): Boolean {

        when (motionEvent.actionMasked) {
            ACTION_DOWN, ACTION_UP, ACTION_CANCEL -> TrayCard.updateSelectionState(c)
        }

        return false
    }

    @OnEvent(ClickEvent::class)
    @JvmStatic fun onClick(c: ComponentContext, @FromEvent view: View) {
        c.toast("Click")
    }

    @OnEvent(LongClickEvent::class)
    @JvmStatic fun onLongClick(
            context: ComponentContext,
            @FromEvent view: View,
            @Prop action: Action
//            @Prop onActionRemoved: (Int) -> Unit
    ): Boolean {

        if (action.actioState == ActionState.PENDING) {
            with(context) {
                alert {
                    items(listOf("Eliminar", "Reenviar"), {
//                        when (it) {
//                            0 -> onActionRemoved.invoke(action.id)
//                        }
                        dismiss()
                    })
                }.show()
            }
        }
        return true
    }

    @JvmStatic @ColorRes
    private fun colorByState(state: ActionState): Int = when (state) {
        ActionState.FINISH -> R.color.colorBgSuccess
        ActionState.PENDING -> R.color.colorBgPending
    }
}