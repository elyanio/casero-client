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
            @State isSelected: Boolean,
            @State isUnread: Boolean
    ): ComponentLayout = Card.create(c)
            .content(TrayCardContent.create(c)
                    .action(action)
                    .clientInfo(clientInfo))
            .cornerRadiusRes(R.dimen.card_radius)
            .elevationRes(R.dimen.card_elevation)
            .cardBackgroundColorRes(if (isSelected) R.color.colorAccent else colorByState(action.actioState, isUnread))
            .withLayout()
            .touchHandler(TrayCard.onTouch(c))
            .clickHandler(TrayCard.onClick(c))
            .longClickHandler(TrayCard.onLongClick(c))
            .build()

    @OnUpdateState
    @JvmStatic fun updateSelectionState(isSelected: StateValue<Boolean>) {
        isSelected.set(!isSelected.get())
    }

    @OnUpdateState
    @JvmStatic fun updateUnreadState(isUnread: StateValue<Boolean>) {
        isUnread.set(!isUnread.get())
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

    @OnCreateInitialState
    @JvmStatic fun createInitialState(
            c: ComponentContext,
            isUnread: StateValue<Boolean>,
            @Prop action: Action) {

        isUnread.set(action.isUnread)
    }

    @OnEvent(ClickEvent::class)
    @JvmStatic fun onClick(
            c: ComponentContext,
            @FromEvent view: View,
            @Prop onActionRead: () -> Unit,
            @State isUnread: Boolean) {
        if (isUnread) {
            onActionRead.invoke()
            TrayCard.updateUnreadState(c)
        }
    }

    @OnEvent(LongClickEvent::class)
    @JvmStatic fun onLongClick(
            context: ComponentContext,
            @FromEvent view: View,
            @Prop action: Action,
            @Prop onActionRemoved: (Int) -> Unit
    ): Boolean {

        if (action.actioState == ActionState.PENDING) {
            with(context) {
                alert {
                    items(listOf("Eliminar", "Reenviar"), {
                        when (it) {
                            0 -> onActionRemoved.invoke(action.id)
                        }
                        dismiss()
                    })
                }.show()
            }
        }
        return true
    }

    @JvmStatic @ColorRes
    private fun colorByState(state: ActionState, isUnread: Boolean): Int = when (state) {
        ActionState.FINISH -> if (isUnread) R.color.colorBgSuccessUnread else R.color.colorBgSuccess
        ActionState.PENDING -> R.color.colorBgPending
    }
}