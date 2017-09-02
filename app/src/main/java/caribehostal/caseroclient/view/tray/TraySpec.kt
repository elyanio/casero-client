package caribehostal.caseroclient.view.tray

import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.Recycler
import com.facebook.litho.widget.RecyclerBinder

/**
 * @author rainermf
 */
@LayoutSpec
object TraySpec {

    @OnCreateLayout
    @JvmStatic fun onCreateLayout(
            c: ComponentContext,
            @Prop recyclerBinder: RecyclerBinder
    ): ComponentLayout = Recycler.create(c)
            .binder(recyclerBinder)
            .buildWithLayout()
}