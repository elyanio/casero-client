package caribehostal.caseroclient.datamodel;

import android.support.annotation.StringRes;

import caribehostal.caseroclient.R;

/**
 * @author rainermf
 */

public enum ActionState {
    PENDING, FINISH;

    @StringRes
    public int toLocalizedString() {
        switch (this) {
            case PENDING:
                return R.string.text_action_pending;
            case FINISH:
                return R.string.text_action_finished;
        }
        return 0;
    }
}
