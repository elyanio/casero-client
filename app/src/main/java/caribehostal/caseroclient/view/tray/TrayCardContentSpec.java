package caribehostal.caseroclient.view.tray;

import com.facebook.litho.Column;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.ComponentLayout.ContainerBuilder;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Text;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeFormatterBuilder;
import org.threeten.bp.format.FormatStyle;

import java.util.Date;

import static com.facebook.yoga.YogaEdge.ALL;
import static org.threeten.bp.format.DateTimeFormatter.ISO_LOCAL_DATE;

/**
 * @author rainermf
 */
@LayoutSpec
class TrayCardContentSpec {

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
            ComponentContext context,
            @Prop String[] passports,
            @Prop LocalTime arrivalTime,
            @Prop LocalDate checkInDate,
            @Prop LocalDate checkOutDate
    ) {
        ContainerBuilder builder = Column.create(context)
                .child(Text.create(context)
                        .text("Entrada: " + checkInDate.format(LONG_DATE))
                        .textSizeSp(14))
                .child(Text.create(context)
                        .text("Salida: " + checkOutDate.format(LONG_DATE))
                        .textSizeSp(14))
                .child(Text.create(context)
                        .text("Pasaportes"));
        for (String passport : passports) {
            builder.child(Text.create(context)
                    .text(passport));
        }
        builder.child(Text.create(context)
                .text(arrivalTime.toString()));
        return builder.build();
    }

    private static final DateTimeFormatter LONG_DATE =
            DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
}
