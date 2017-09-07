package caribehostal.caseroclient.view.tray;

import android.support.annotation.ColorInt;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import caribehostal.caseroclient.R;

/**
 * @author rainermf
 */

public class MessageHolder extends BaseEpoxyHolder {

    @BindView(R.id.check_in_text) TextView checkInText;
    @BindView(R.id.check_out_text) TextView checkOutText;
    @BindView(R.id.action_id_text) TextView actionIdText;
    @BindView(R.id.passports_list) TextView passportList;
    @BindView(R.id.confirmation_codes_list) TextView confirmationCodesList;
    @BindView(R.id.time_text) TextView timeText;
    @BindView(R.id.state_text) TextView stateText;
    @BindView(R.id.action_card) CardView actionCard;
    @BindView(R.id.check_in_title) TextView checkInTitle;
    @BindView(R.id.check_out_title) TextView checkOutTitle;
    @BindView(R.id.passport_title) TextView passportTitle;
    @BindView(R.id.confirmation_code_title) TextView confirmationCodeTitle;

    void setTextColor(@ColorInt int color) {
        List<TextView> textViews = Arrays.asList(checkInText, checkOutText, actionIdText, passportList,
                confirmationCodesList, timeText, stateText,
                checkInTitle, checkOutTitle, passportTitle, confirmationCodeTitle);
        for (TextView view : textViews) {
            view.setTextColor(color);
        }
    }
}
