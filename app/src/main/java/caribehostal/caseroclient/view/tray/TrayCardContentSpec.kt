package caribehostal.caseroclient.view.tray

import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaEdge.ALL
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

/**
 * @author rainermf
 */
@LayoutSpec
object TrayCardContentSpec {

    @OnCreateLayout
    @JvmStatic fun onCreateLayout(
            context: ComponentContext,
            @Prop passports: Array<String>,
            @Prop arrivalTime: LocalTime,
            @Prop checkInDate: LocalDate,
            @Prop checkOutDate: LocalDate
    ): ComponentLayout = Column.create(context)
            .paddingDip(ALL, 16)
            .child(Text.create(context)
                    .text("$checkInDate / $checkOutDate"))
            .child(Text.create(context)
                    .text("Pasaportes"))
            .apply {
                passports.forEach {
                    child(Text.create(context)
                            .text(it))
                }
            }
            .child(Text.create(context)
                    .text(arrivalTime.toString()))
            .build()
}