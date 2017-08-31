package caribehostal.caseroclient.view.tray;

import com.facebook.litho.Column;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Card;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

/**
 * @author rainermf
 */
@LayoutSpec
public class TrayCardSpec {

    @OnCreateLayout
    public static ComponentLayout onCreateLayout(
            ComponentContext context,
            @Prop String[] passports,
            @Prop LocalTime arrivalTime,
            @Prop LocalDate checkInDate,
            @Prop LocalDate checkOutDate
            ) {
        return Column.create(context)
                .child(Card.create(context)
                        .content(TrayCardContent.create(context)
                                .passports(passports)
                                .arrivalTime(arrivalTime)
                                .checkInDate(checkInDate)
                                .checkOutDate(checkOutDate)
                        ))
                .build();
    }
}
