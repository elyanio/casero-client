package caribehostal.caseroclient.view.tray

import caribehostal.caseroclient.datamodel.Action
import caribehostal.caseroclient.datamodel.ClientInfo
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import java.util.*

/**
 * @author rainermf
 */
class Message(
        val action: Action,
        val clientInfo: Array<ClientInfo>,
        val onActionRemoved: (Int) -> Unit,
        val onActionRead: () -> Unit
) : TrayData {

    override fun createComponent(c: ComponentContext): Component<*> = TrayCard.create(c)
            .action(action)
            .clientInfo(clientInfo)
            .onActionRemoved(onActionRemoved)
            .onActionRead(onActionRead)
            .build()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Message

        if (action != other.action) return false
        if (!Arrays.equals(clientInfo, other.clientInfo)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = action.hashCode()
        result = 31 * result + Arrays.hashCode(clientInfo)
        return result
    }


}