package caribehostal.caseroclient.view.tray

import android.graphics.Typeface.BOLD
import caribehostal.caseroclient.R
import caribehostal.caseroclient.R.dimen.normal_text_size
import caribehostal.caseroclient.datamodel.Action
import caribehostal.caseroclient.datamodel.ActionState.FINISH
import caribehostal.caseroclient.datamodel.ClientInfo
import caribehostal.caseroclient.util.LONG_DATE
import caribehostal.caseroclient.util.SHORT_TIME
import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.Row
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaEdge.ALL
import com.facebook.yoga.YogaEdge.VERTICAL

/**
 * @author rainermf
 */
@LayoutSpec
object TrayCardContentSpec {

    @OnCreateLayout
    @JvmStatic fun onCreateLayout(
            context: ComponentContext,
            @Prop action: Action,
            @Prop clientInfo: Array<ClientInfo>
    ): ComponentLayout = Column.create(context)
            .marginRes(ALL, R.dimen.normal_margin)
            .child(Row.create(context)
                    .child(Text.create(context)
                            .textRes(R.string.check_in_text)
                            .textStyle(BOLD)
                            .textSizeRes(normal_text_size))
                    .child(Text.create(context)
                            .text(": ${action.checkIn.format(LONG_DATE)}")
                            .textSizeRes(normal_text_size)))
            .child(Row.create(context)
                    .child(Text.create(context)
                            .textRes(R.string.check_out_text)
                            .textStyle(BOLD)
                            .textSizeRes(normal_text_size))
                    .child(Text.create(context)
                            .text(": ${action.checkOut.format(LONG_DATE)}")
                            .textSizeRes(normal_text_size)))
            .child(clientInfoTable(context, clientInfo, action.actioState == FINISH))
            .child(Text.create(context)
                    .text(action.sendTime.toLocalTime().format(SHORT_TIME)))
            .build()

    @JvmStatic private fun clientInfoTable(
            context: ComponentContext,
            clientInfo: Array<ClientInfo>,
            showCodeColumn: Boolean
    ): ComponentLayout = Row.create(context)
            .paddingDip(VERTICAL, 8)
            .child(passportColumn(context, clientInfo))
            .child(if(showCodeColumn) codeColumn(context, clientInfo) else null)
            .build()

    @JvmStatic private fun codeColumn(
            context: ComponentContext,
            clientInfo: Array<ClientInfo>
    ): ComponentLayout.ContainerBuilder? = Column.create(context)
            .flexGrow(1f)
            .child(Text.create(context)
                    .textRes(R.string.client_casero_code)
                    .textStyle(BOLD)
                    .textSizeRes(normal_text_size))
            .apply {
                for ((_, caseroCode) in clientInfo) {
                    child(Text.create(context)
                            .text(caseroCode)
                            .textSizeRes(normal_text_size))
                }
            }

    private fun passportColumn(
            context: ComponentContext,
            clientInfo: Array<ClientInfo>
    ): ComponentLayout.ContainerBuilder? = Column.create(context)
            .flexGrow(1f)
            .child(Text.create(context)
                    .textRes(R.string.client_passport)
                    .textSizeRes(normal_text_size)
                    .textStyle(BOLD))
            .apply {
                for ((passport, _) in clientInfo) {
                    child(Text.create(context)
                            .text(passport)
                            .textSizeRes(normal_text_size))
                }
            }
}
