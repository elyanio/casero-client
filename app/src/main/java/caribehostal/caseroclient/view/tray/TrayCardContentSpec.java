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

import static com.facebook.yoga.YogaEdge.ALL;

/**
 * @author rainermf
 */
@LayoutSpec
public class TrayCardContentSpec {

    @OnCreateLayout
    public static ComponentLayout onCreateLayout(
            ComponentContext context,
            @Prop String[] passports,
            @Prop LocalTime arrivalTime,
            @Prop LocalDate checkInDate,
            @Prop LocalDate checkOutDate
    ) {
        ContainerBuilder builder = Column.create(context)
                .paddingDip(ALL, 16)
                .child(Text.create(context)
                        .text(checkInDate + " / " + checkOutDate))
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
}
