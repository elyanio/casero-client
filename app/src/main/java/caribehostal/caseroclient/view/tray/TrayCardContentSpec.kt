package caribehostal.caseroclient.view.tray

import android.graphics.Color
import android.graphics.Typeface.BOLD
import caribehostal.caseroclient.R
import caribehostal.caseroclient.R.dimen.normal_text_size
import caribehostal.caseroclient.datamodel.ActionState
import caribehostal.caseroclient.datamodel.ActionState.FINISH
import caribehostal.caseroclient.datamodel.ClientInfo
import caribehostal.caseroclient.util.LONG_DATE
import caribehostal.caseroclient.util.SHORT_TIME
import com.facebook.litho.*
import com.facebook.litho.annotations.*
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaEdge.ALL
import com.facebook.yoga.YogaEdge.VERTICAL
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime


/**
 * @author rainermf
 */
@LayoutSpec
object TrayCardContentSpec {

    @OnCreateLayout
    @JvmStatic fun onCreateLayout(
            c: ComponentContext,
            @Prop checkIn: LocalDate,
            @Prop checkOut: LocalDate,
            @Prop actionState: ActionState,
            @Prop sendTime: LocalDateTime,
            @Prop clientInfo: Array<ClientInfo>,
            @Prop isUnread: Boolean
    ): ComponentLayout = Column.create(c)
            .marginRes(ALL, R.dimen.normal_margin)
            .child(Row.create(c)
                    .child(Text.create(c)
                            .textRes(R.string.check_in_text)
                            .textStyle(BOLD)
                            .textColor(textColor(isUnread))
                            .textSizeRes(normal_text_size))
                    .child(Text.create(c)
                            .text(": ${checkIn.format(LONG_DATE)}")
                            .textColor(textColor(isUnread))
                            .textSizeRes(normal_text_size)))
            .child(Row.create(c)
                    .child(Text.create(c)
                            .textRes(R.string.check_out_text)
                            .textStyle(BOLD)
                            .textColor(textColor(isUnread))
                            .textSizeRes(normal_text_size))
                    .child(Text.create(c)
                            .text(": ${checkOut.format(LONG_DATE)}")
                            .textColor(textColor(isUnread))
                            .textSizeRes(normal_text_size)))
            .child(clientInfoTable(c, clientInfo, actionState == FINISH, isUnread))
            .child(Text.create(c)
                    .text(sendTime.toLocalTime().format(SHORT_TIME))
                    .textColor(textColor(isUnread)))
            .build()

    @JvmStatic private fun clientInfoTable(
            c: ComponentContext,
            clientInfo: Array<ClientInfo>,
            showCodeColumn: Boolean,
            isUnread: Boolean
    ): ComponentLayout = Row.create(c)
            .paddingDip(VERTICAL, 8)
            .child(passportColumn(c, clientInfo, isUnread))
            .child(if (showCodeColumn) codeColumn(c, clientInfo, isUnread) else null)
            .build()

    @JvmStatic private fun codeColumn(
            c: ComponentContext,
            clientInfo: Array<ClientInfo>,
            isUnread: Boolean
    ): ComponentLayout.ContainerBuilder? = Column.create(c)
            .flexGrow(1f)
            .child(Text.create(c)
                    .textRes(R.string.client_casero_code)
                    .textColor(textColor(isUnread))
                    .textStyle(BOLD)
                    .textSizeRes(normal_text_size))
            .apply {
                for ((_, caseroCode) in clientInfo) {
                    child(Text.create(c)
                            .text(caseroCode)
                            .textColor(textColor(isUnread))
                            .textSizeRes(normal_text_size))
                }
            }

    private fun passportColumn(
            c: ComponentContext,
            clientInfo: Array<ClientInfo>,
            isUnread: Boolean
    ): ComponentLayout.ContainerBuilder? = Column.create(c)
            .flexGrow(1f)
            .child(Text.create(c)
                    .textRes(R.string.client_passport)
                    .textSizeRes(normal_text_size)
                    .textColor(textColor(isUnread))
                    .textStyle(BOLD))
            .apply {
                for ((passport, _) in clientInfo) {
                    child(Text.create(c)
                            .text(passport)
                            .textColor(textColor(isUnread))
                            .textSizeRes(normal_text_size))
                }
            }

    @JvmStatic
    private fun textColor(isUnread: Boolean): Int = if (isUnread) Color.WHITE else Color.BLACK

}

