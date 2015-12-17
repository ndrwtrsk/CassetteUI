package nd.rw.cassetteui.data.entity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import nd.rw.cassetteui.BuildConfig;
import nd.rw.cassetteui.app.CassetteApplication;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,sdk = 21)
public class CassetteEntityTest {

    private Realm realm;

    @Before
    public void beforeSetUp(){
        CassetteApplication cassetteApplication = (CassetteApplication) RuntimeEnvironment.application;
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(cassetteApplication).build();
        Realm.setDefaultConfiguration(realmConfig);
        realm = Realm.getDefaultInstance();
    }

    @Test
    public void testCreatingAndPersisting(){
        //  Act
        realm.beginTransaction();
        CassetteEntity cassetteEntity = realm.createObject(CassetteEntity.class);
        cassetteEntity.setId(1);
        cassetteEntity.setTitle("Title");
        cassetteEntity.setDate(new Date());
        realm.commitTransaction();

        //Assert
        CassetteEntity recentlyCreatedCassette = realm.where(CassetteEntity.class).findFirst();
        Assert.assertEquals(1, recentlyCreatedCassette.getId());
    }

}
