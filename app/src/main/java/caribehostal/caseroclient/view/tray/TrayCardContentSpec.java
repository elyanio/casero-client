package caribehostal.caseroclient.view.tray;

import com.facebook.litho.Column;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.ComponentLayout.ContainerBuilder;
import com.facebook.litho.Row;
import com.facebook.litho.TextContent;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import caribehostal.caseroclient.R;
import caribehostal.caseroclient.datamodel.ClientInfo;

import static android.graphics.Typeface.BOLD;
import static caribehostal.caseroclient.R.dimen.normal_text_size;
import static caribehostal.caseroclient.util.DateTimeFormatters.LONG_DATE;
import static caribehostal.caseroclient.util.DateTimeFormatters.SHORT_TIME;

/**
 * @author rainermf
 */
@LayoutSpec
class TrayCardContentSpec {

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
            ComponentContext context,
            @Prop ClientInfo[] clientInfo,
            @Prop LocalTime arrivalTime,
            @Prop LocalDate checkInDate,
            @Prop LocalDate checkOutDate
    ) {
        return Column.create(context)
                .child(Row.create(context)
                        .child(Text.create(context)
                                .textRes(R.string.check_in_text)
                                .textStyle(BOLD)
                                .textSizeRes(normal_text_size))
                        .child(Text.create(context)
                                .text(": " + checkInDate.format(LONG_DATE))
                                .textSizeRes(normal_text_size)))
                .child(Row.create(context)
                        .child(Text.create(context)
                                .textRes(R.string.check_out_text)
                                .textStyle(BOLD)
                                .textSizeRes(normal_text_size))
                        .child(Text.create(context)
                                .text(": " + checkOutDate.format(LONG_DATE))
                                .textSizeRes(normal_text_size)))
                .child(clientInfoTable(context, clientInfo))
                .child(Text.create(context)
                        .text(arrivalTime.format(SHORT_TIME)))
                .build();
    }

    private static ComponentLayout clientInfoTable(ComponentContext context, ClientInfo[] clientInfo) {
        ContainerBuilder passportColumn = Column.create(context)
                .flexGrow(1)
                .child(Text.create(context)
                        .textRes(R.string.client_passport)
                        .textSizeRes(normal_text_size)
                        .textStyle(BOLD));
        ContainerBuilder codeColumn = Column.create(context)
                .flexGrow(1)
                .child(Text.create(context)
                        .textRes(R.string.client_casero_code)
                        .textStyle(BOLD)
                        .textSizeRes(normal_text_size));

        for (ClientInfo info : clientInfo) {
            passportColumn.child(Text.create(context)
                    .text(info.getPassport())
                    .textSizeRes(normal_text_size));
            codeColumn.child(Text.create(context)
                    .text(info.getCaseroCode())
                    .textSizeRes(normal_text_size));
        }
        return Row.create(context)
                .child(passportColumn)
                .child(codeColumn)
                .paddingDip(YogaEdge.VERTICAL, 8)
                .build();
    }
}
