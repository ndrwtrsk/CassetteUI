package nd.rw.cassette.app;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class CassetteApplication extends Application {

    public int nice = 69;

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

}
