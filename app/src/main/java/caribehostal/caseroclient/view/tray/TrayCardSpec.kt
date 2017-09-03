package caribehostal.caseroclient.view.tray

import android.content.Context
import android.support.annotation.ColorRes
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import caribehostal.caseroclient.R
import caribehostal.caseroclient.datamodel.Action
import caribehostal.caseroclient.datamodel.ActionState
import caribehostal.caseroclient.datamodel.ClientInfo
import caribehostal.caseroserver.comunication.SmsSender
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
    @JvmStatic fun updateSelectionState(isSelected: StateValue<Boolean>, @Param selection: Boolean) {
        isSelected.set(selection)
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
            ACTION_MOVE -> TrayCard.updateSelectionState(c, true)
            ACTION_UP -> TrayCard.updateSelectionState(c, false)
            ACTION_CANCEL -> TrayCard.updateSelectionState(c, false)
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

    @JvmStatic private val smsSender = SmsSender()

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
                            1 -> resendAction(context, action)
                        }
                        dismiss()
                    })
                }.show()
            }
        }
        return true
    }

    @JvmStatic private fun resendAction(context: Context, action: Action) {
        smsSender.sendSms(action)
        context.toast(R.string.message_action_resent)
    }

    @JvmStatic @ColorRes
    private fun colorByState(state: ActionState, isUnread: Boolean): Int = when (state) {
        ActionState.FINISH -> if (isUnread) R.color.colorBgSuccessUnread else R.color.colorBgSuccess
        ActionState.PENDING -> R.color.colorBgPending
    }
}