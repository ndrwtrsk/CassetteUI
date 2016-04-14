package nd.rw.cassette.data.repository.datasource;

import android.test.AndroidTestCase;

import io.realm.Realm;
import nd.rw.cassette.data.entity.CassetteEntity;

public class RealmCassetteDataStoreTest extends AndroidTestCase {

    private Realm realm = Realm.getInstance(getContext());

    public void setUp() throws Exception {
        super.setUp();

    }

    public void testAdding(){
        //  Arrange
        CassetteEntity cassetteEntity = new CassetteEntity();
        cassetteEntity.setId(2);
    }

    public void tearDown() throws Exception {
    }
}