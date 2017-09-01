package caribehostal.caseroclient.view.tray;

import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import com.facebook.litho.ClickEvent;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.FromEvent;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.OnEvent;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Card;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import static com.facebook.yoga.YogaEdge.ALL;

/**
 * @author rainermf
 */
@LayoutSpec
class TrayCardSpec {

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
            ComponentContext context,
            @Prop String[] passports,
            @Prop LocalTime arrivalTime,
            @Prop LocalDate checkInDate,
            @Prop LocalDate checkOutDate
    ) {
        return Card.create(context)
                .content(TrayCardContent.create(context)
                        .passports(passports)
                        .arrivalTime(arrivalTime)
                        .checkInDate(checkInDate)
                        .checkOutDate(checkOutDate))
                .cornerRadiusDip(6)
                .elevationDip(6)
                .withLayout()
                .paddingDip(ALL, 8)
                .clickHandler(TrayCard.onClick(context))
                .build();
    }

    @OnEvent(ClickEvent.class)
    static void onClick(ComponentContext context, @FromEvent View view) {
        Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
    }
}
