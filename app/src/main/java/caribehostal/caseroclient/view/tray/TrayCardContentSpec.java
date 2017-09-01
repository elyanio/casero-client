package caribehostal.caseroclient.view.tray;

import com.facebook.litho.Column;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.ComponentLayout.ContainerBuilder;
import com.facebook.litho.Row;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Text;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import caribehostal.caseroclient.datamodel.ClientInfo;

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
        ContainerBuilder passportColumn = Column.create(context)
                .flexGrow(1)
                .child(Text.create(context)
                        .text("Pasaporte"));
        ContainerBuilder codeColumn = Column.create(context)
                .flexGrow(1)
                .child(Text.create(context)
                        .text("Confirmación"));
        ContainerBuilder clientsTable = Row.create(context)
                .child(passportColumn)
                .child(codeColumn);
        for (ClientInfo info : clientInfo) {
            passportColumn.child(Text.create(context)
                    .text(info.getPassport()));
            codeColumn.child(Text.create(context)
                    .text(info.getCaseroCode()));
        }

        return Column.create(context)
                .child(Text.create(context)
                        .text("Entrada: " + checkInDate.format(LONG_DATE))
                        .textSizeSp(14))
                .child(Text.create(context)
                        .text("Salida: " + checkOutDate.format(LONG_DATE))
                        .textSizeSp(14))
                .child(clientsTable)
                .child(Text.create(context)
                        .text(arrivalTime.toString()))
                .build();
    }

    private static final DateTimeFormatter LONG_DATE =
            DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
}
