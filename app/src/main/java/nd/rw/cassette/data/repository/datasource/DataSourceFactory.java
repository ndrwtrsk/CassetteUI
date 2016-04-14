package nd.rw.cassette.data.repository.datasource;

public class DataSourceFactory {

    public static CassetteDataStore getRealmCassetteDataStore(){
        return new RealmCassetteDataStore();
    }

    public static RecordingDataStore getRealmRecordingDataStore(){
        return new RealmRecordingDataStore();
    }

}
