package caribehostal.caseroclient;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

/**
 * @author rainermf
 * @since 20/2/2017
 */
public class CaseroClientApplication extends Application {

    private static CaseroClientApplication instance;

    public static CaseroClientApplication instance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AndroidThreeTen.init(this);
    }
}
