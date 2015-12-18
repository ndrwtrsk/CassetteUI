package nd.rw.cassetteui.data.repository.datasource;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import nd.rw.cassetteui.data.entity.CassetteEntity;

public class RealmCassetteDataStore implements CassetteDataStore {

    private Realm mRealm;
    private RealmResults<CassetteEntity> mAllCassettes;

    public RealmCassetteDataStore() {
        mRealm = Realm.getDefaultInstance();
        mAllCassettes = mRealm.where(CassetteEntity.class).findAll();
    }

    public RealmCassetteDataStore(Context context){
        mRealm = Realm.getInstance(context);
        mAllCassettes = mRealm.where(CassetteEntity.class).findAll();
    }

    @Override
    public List<CassetteEntity> getAll() {
        return mAllCassettes;
    }

    @Override
    public CassetteEntity get(final int id) {
        return mRealm.where(CassetteEntity.class)
                        .equalTo("id", id)
                        .findFirst();
    }

    @Override
    public CassetteEntity create(CassetteEntity cassetteEntity) {
        if (cassetteEntity == null) {
            return null;
        }
        cassetteEntity.setId(this.getNewPkValue());
        mRealm.beginTransaction();
        CassetteEntity realmCassette = mRealm.copyToRealm(cassetteEntity);
        mRealm.commitTransaction();
        return realmCassette;
    }

    @Override
    public boolean update(CassetteEntity cassetteEntity) {
        if (cassetteEntity == null) {
            return false;
        }
        mRealm.beginTransaction();
        CassetteEntity updatedCassette = mRealm.copyToRealmOrUpdate(cassetteEntity);
        mRealm.commitTransaction();

        return updatedCassette != null;
    }

    @Override
    public boolean update(int id, String title, String description) {
        CassetteEntity retrievedCassette = this.get(id);
        if (retrievedCassette == null) {
            return false;
        }
        mRealm.beginTransaction();
        retrievedCassette.setTitle(title);
        retrievedCassette.setDescription(description);
        retrievedCassette = mRealm.copyToRealmOrUpdate(retrievedCassette);
        mRealm.commitTransaction();

        return retrievedCassette != null;
    }

    @Override
    public boolean delete(CassetteEntity cassetteEntity) {
        if (cassetteEntity == null) {
            return false;
        }
        int id = cassetteEntity.getId();
        mRealm.beginTransaction();
        cassetteEntity.removeFromRealm();
        mRealm.commitTransaction();

        return !exists(id); //CHECK THAT IT DOESN'T EXIST!
    }

    @Override
    public boolean delete(final int id) {
        return this.delete(this.get(id));
    }

    @Override
    public long count() {
        return mRealm.where(CassetteEntity.class).count();
    }

    private boolean exists(final int id){
        return mRealm.where(CassetteEntity.class)
                        .equalTo("id", id).findFirst() != null;
    }

    @Override
    public int getNewPkValue() {
        return mRealm.where(CassetteEntity.class).max("id").intValue() + 1;
    }
}
