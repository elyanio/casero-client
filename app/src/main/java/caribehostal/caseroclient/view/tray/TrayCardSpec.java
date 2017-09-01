package caribehostal.caseroclient.view.tray;

import android.view.View;
import android.widget.Toast;

import com.facebook.litho.ClickEvent;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.LongClickEvent;
import com.facebook.litho.annotations.FromEvent;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.OnEvent;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Card;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import caribehostal.caseroclient.datamodel.ClientInfo;

import static com.facebook.yoga.YogaEdge.ALL;

/**
 * @author rainermf
 */
@LayoutSpec
class TrayCardSpec {

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
            ComponentContext context,
            @Prop ClientInfo[] clientInfo,
            @Prop LocalTime arrivalTime,
            @Prop LocalDate checkInDate,
            @Prop LocalDate checkOutDate
    ) {
        return Card.create(context)
                .content(TrayCardContent.create(context)
                        .clientInfo(clientInfo)
                        .arrivalTime(arrivalTime)
                        .checkInDate(checkInDate)
                        .checkOutDate(checkOutDate))
                .cornerRadiusDip(6)
                .elevationDip(6)
                .withLayout()
                .paddingDip(ALL, 8)
                .clickHandler(TrayCard.onClick(context))
                .longClickHandler(TrayCard.onLongClick(context))
                .build();
    }

    @OnEvent(ClickEvent.class)
    static void onClick(ComponentContext context, @FromEvent View view) {
        Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
    }

    @OnEvent(LongClickEvent.class)
    static boolean onLongClick(ComponentContext context, @FromEvent View view) {
        Toast.makeText(context, "long hold", Toast.LENGTH_SHORT).show();
        return true;
    }
}
