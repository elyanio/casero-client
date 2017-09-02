package caribehostal.caseroclient.view.tray

import com.facebook.litho.Component
import com.facebook.litho.ComponentContext

/**
 * @author rainermf
 */
interface TrayData {
    fun createComponent(c: ComponentContext): Component<*>
}