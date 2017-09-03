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
            c: ComponentContext,
            @Prop action: Action,
            @Prop clientInfo: Array<ClientInfo>
    ): ComponentLayout = Column.create(c)
            .marginRes(ALL, R.dimen.normal_margin)
            .child(Row.create(c)
                    .child(Text.create(c)
                            .textRes(R.string.check_in_text)
                            .textStyle(BOLD)
                            .textSizeRes(normal_text_size))
                    .child(Text.create(c)
                            .text(": ${action.checkIn.format(LONG_DATE)}")
                            .textSizeRes(normal_text_size)))
            .child(Row.create(c)
                    .child(Text.create(c)
                            .textRes(R.string.check_out_text)
                            .textStyle(BOLD)
                            .textSizeRes(normal_text_size))
                    .child(Text.create(c)
                            .text(": ${action.checkOut.format(LONG_DATE)}")
                            .textSizeRes(normal_text_size)))
            .child(clientInfoTable(c, clientInfo, action.actioState == FINISH))
            .child(Text.create(c)
                    .text(action.sendTime.toLocalTime().format(SHORT_TIME)))
            .build()

    @JvmStatic private fun clientInfoTable(
            c: ComponentContext,
            clientInfo: Array<ClientInfo>,
            showCodeColumn: Boolean
    ): ComponentLayout = Row.create(c)
            .paddingDip(VERTICAL, 8)
            .child(passportColumn(c, clientInfo))
            .child(if (showCodeColumn) codeColumn(c, clientInfo) else null)
            .build()

    @JvmStatic private fun codeColumn(
            c: ComponentContext,
            clientInfo: Array<ClientInfo>
    ): ComponentLayout.ContainerBuilder? = Column.create(c)
            .flexGrow(1f)
            .child(Text.create(c)
                    .textRes(R.string.client_casero_code)
                    .textStyle(BOLD)
                    .textSizeRes(normal_text_size))
            .apply {
                for ((_, caseroCode) in clientInfo) {
                    child(Text.create(c)
                            .text(caseroCode)
                            .textSizeRes(normal_text_size))
                }
            }

    private fun passportColumn(
            c: ComponentContext,
            clientInfo: Array<ClientInfo>
    ): ComponentLayout.ContainerBuilder? = Column.create(c)
            .flexGrow(1f)
            .child(Text.create(c)
                    .textRes(R.string.client_passport)
                    .textSizeRes(normal_text_size)
                    .textStyle(BOLD))
            .apply {
                for ((passport, _) in clientInfo) {
                    child(Text.create(c)
                            .text(passport)
                            .textSizeRes(normal_text_size))
                }
            }
}

