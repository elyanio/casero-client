package caribehostal.caseroclient.dataaccess;

import android.content.Context;

import java.io.File;

import caribehostal.caseroclient.CaseroClientApplication;
import caribehostal.caseroclient.datamodel.Models;
import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;

import static android.os.Environment.getExternalStorageDirectory;

/**
 * Created by asio on 8/17/2017.
 */

public enum DataStoreHolder {
    INSTANCE;

    private EntityDataStore<Persistable> entityDataStore;
    private File directory = new File(getExternalStorageDirectory().getAbsolutePath() + "/Casero");
    private File dbFile = new File(directory.getAbsolutePath() + "/caseroCliente.db");
    private int DB_VERSION = 1;

    public EntityDataStore<Persistable> getEntityDataStore() {
        return entityDataStore;
    }

    public EntityDataStore<Persistable> getDataStore() {
        if (entityDataStore == null) {
            entityDataStore = createDataStore(CaseroClientApplication.instance(), dbFile);
        }
        return entityDataStore;
    }

    private EntityDataStore<Persistable> createDataStore(Context context, File dbFile) {
        String dbName = dbFile.getAbsolutePath();
        Configuration configuration;
        DatabaseSource source = new DatabaseSource(context, Models.DEFAULT, dbName, DB_VERSION);
        configuration = source.getConfiguration();
        return new EntityDataStore<>(configuration);
    }

    public boolean existDbFile() {
        return dbFile.exists();
    }

    public File getDbFile() {
        return dbFile;
    }
}
