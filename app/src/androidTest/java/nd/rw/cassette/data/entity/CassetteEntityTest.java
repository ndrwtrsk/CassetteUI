package nd.rw.cassette.data.entity;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import java.util.Date;

import io.realm.Realm;

public class CassetteEntityTest extends AndroidTestCase{

    private Realm realm;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        realm = Realm.getInstance(getContext());
    }

    public void testCreatingAndRetrieving(){
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
