package caribehostal.caseroclient;

import android.app.Application;

import com.facebook.soloader.SoLoader;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.Locale;

import caribehostal.caseroclient.dataaccess.DataStoreHolder;
import io.requery.Persistable;
import io.requery.sql.EntityDataStore;

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
        SoLoader.init(this, false);
    }

    /**
     * @deprecated Use DataStoreHolder.INSTANCE.getDataStore()
     */
    @Deprecated
    public EntityDataStore<Persistable> getDataStore() {
        return DataStoreHolder.INSTANCE.getDataStore();
    }
}
