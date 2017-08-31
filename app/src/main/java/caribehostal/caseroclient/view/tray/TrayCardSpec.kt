package caribehostal.caseroclient.view.tray

import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.Card
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

/**
 * @author rainermf
 */
@LayoutSpec
object TrayCardSpec {

    @OnCreateLayout
    @JvmStatic fun onCreateLayout(
            context: ComponentContext,
            @Prop passports: Array<String>,
            @Prop arrivalTime: LocalTime,
            @Prop checkInDate: LocalDate,
            @Prop checkOutDate: LocalDate
    ) = Column.create(context)
            .child(Card.create(context)
                    .content(TrayCardContent.create(context)
                            .arg1(passports)
                            .arg2(arrivalTime)
                            .arg3(checkInDate)
                            .arg4(checkOutDate)))
            .build()
}