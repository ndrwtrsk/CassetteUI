package nd.rw.cassetteui.data.repository.datasource;

import android.test.AndroidTestCase;

import junit.framework.TestCase;

import io.realm.Realm;
import nd.rw.cassetteui.data.entity.CassetteEntity;

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