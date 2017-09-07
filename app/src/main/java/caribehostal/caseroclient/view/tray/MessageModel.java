package caribehostal.caseroclient.view.tray;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.view.View;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java.util.Collections;
import java.util.List;

import caribehostal.caseroclient.R;
import caribehostal.caseroclient.datamodel.ActionState;
import caribehostal.caseroclient.util.CollectionsKt;
import caribehostal.caseroclient.util.DateTimeFormatters;

import static android.support.v4.content.ContextCompat.getColor;

/**
 * @author rainermf
 */
@EpoxyModelClass(layout = R.layout.message_view)
abstract class MessageModel extends EpoxyModelWithHolder<MessageHolder> {

    @EpoxyAttribute int actionId;
    @EpoxyAttribute LocalDate checkInDate = LocalDate.MIN;
    @EpoxyAttribute LocalDate checkOutDate = LocalDate.MAX;
    @EpoxyAttribute List<String> passports = Collections.emptyList();
    @EpoxyAttribute List<String> confirmationCodes = Collections.emptyList();
    @EpoxyAttribute LocalDateTime time = LocalDateTime.MIN;
    @EpoxyAttribute ActionState state = ActionState.PENDING;
    @EpoxyAttribute boolean isUnread;
    @EpoxyAttribute View.OnLongClickListener longClickListener;
    @EpoxyAttribute View.OnClickListener clickListener;

    @Override
    public void bind(MessageHolder holder) {
        holder.actionIdText.setText(String.valueOf(actionId));
        holder.checkInText.setText(checkInDate.format(DateTimeFormatters.LONG_DATE));
        holder.checkOutText.setText(checkOutDate.format(DateTimeFormatters.LONG_DATE));
        holder.passportList.setText(CollectionsKt.joinToString(passports, "\n"));
        holder.confirmationCodesList.setText(CollectionsKt.joinToString(confirmationCodes, "\n"));
        holder.timeText.setText(time.format(DateTimeFormatters.SHORT_TIME));
        holder.stateText.setText(state.toLocalizedString());
        Context context = holder.actionCard.getContext();
        holder.actionCard.setCardBackgroundColor(getColor(context, colorByState(state, isUnread)));
        holder.actionCard.setOnLongClickListener(longClickListener);
        holder.actionCard.setOnClickListener(clickListener);
        holder.setTextColor(textColor(isUnread));
    }

    @ColorRes
    private static int colorByState(ActionState state, boolean isUnread) {
        switch (state) {
            case FINISH:
                return (isUnread) ? R.color.colorBgSuccessUnread : R.color.colorBgSuccess;
            case PENDING:
                return R.color.colorBgPending;
        }
        return 0;
    }

    @ColorInt
    private static int textColor(boolean isUnread) {
        return (isUnread) ? Color.WHITE : Color.BLACK;
    }

    @Override
    protected MessageHolder createNewHolder() {
        return new MessageHolder();
    }
}