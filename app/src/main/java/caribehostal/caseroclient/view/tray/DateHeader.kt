package caribehostal.caseroclient.view.tray

import com.facebook.litho.Component
import com.facebook.litho.ComponentContext

/**
 * @author rainermf
 */
data class DateHeader(val title: String) : TrayData {

    override fun createComponent(c: ComponentContext): Component<DayTitle> = DayTitle.create(c)
            .title(title)
            .build()
}