package caribehostal.caseroclient.view.tray

import android.support.annotation.ColorRes
import android.view.View
import caribehostal.caseroclient.R
import caribehostal.caseroclient.datamodel.Action
import caribehostal.caseroclient.datamodel.ActionState
import caribehostal.caseroclient.datamodel.ClientInfo
import com.facebook.litho.ClickEvent
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.LongClickEvent
import com.facebook.litho.annotations.*
import com.facebook.litho.widget.Card
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

/**
 * @author rainermf
 */
@LayoutSpec
object TrayCardSpec {

    @OnCreateLayout
    @JvmStatic fun onCreateLayout(
            context: ComponentContext,
            @Prop action: Action,
            @Prop clientInfo: Array<ClientInfo>
    ): ComponentLayout = Card.create(context)
            .content(TrayCardContent.create(context)
                    .action(action)
                    .clientInfo(clientInfo))
            .cornerRadiusRes(R.dimen.card_radius)
            .elevationRes(R.dimen.card_elevation)
            .cardBackgroundColorRes(colorByState(action.actioState))
            .withLayout()
            .clickHandler(TrayCard.onClick(context))
            .longClickHandler(TrayCard.onLongClick(context))
            .build()

    @OnEvent(ClickEvent::class)
    @JvmStatic fun onClick(context: ComponentContext, @FromEvent view: View) {
        context.longToast("hello")
    }

    @OnEvent(LongClickEvent::class)
    @JvmStatic fun onLongClick(context: ComponentContext, @FromEvent view: View): Boolean {
        context.toast("long hold")
        return true
    }

    @JvmStatic @ColorRes
    private fun colorByState(state: ActionState): Int = when (state) {
        ActionState.FINISH -> R.color.colorBgSuccess
        ActionState.PENDING -> R.color.colorBgPending
    }
}
