package caribehostal.caseroclient.view.tray;

import android.graphics.Color;
import android.text.Layout;

import com.facebook.litho.Column;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;

import static android.text.Layout.Alignment.ALIGN_CENTER;

/**
 * @author rainermf
 */

@LayoutSpec
class DayTitleSpec {

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
            ComponentContext context,
            @Prop String title
    ) {
        return Column.create(context)
                .paddingDip(YogaEdge.ALL, 16)
                .child(Text.create(context)
                        .text(title)
                        .textAlignment(ALIGN_CENTER)
                        .textColor(Color.DKGRAY)
                        .build())
                .build();
    }
}
