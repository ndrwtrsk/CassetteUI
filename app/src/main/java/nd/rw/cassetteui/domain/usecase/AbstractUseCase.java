package nd.rw.cassetteui.domain.usecase;

import java.util.List;

import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.data.repository.CassetteDataRepository;
import nd.rw.cassetteui.data.repository.RecordingDataRepository;
import nd.rw.cassetteui.data.repository.datasource.DataSourceFactory;
import nd.rw.cassetteui.domain.repository.CassetteRepository;
import nd.rw.cassetteui.domain.repository.RecordingRepository;

/**
 * Class which serves as base class for all classes which perform use cases. All business logic is
 * accessed through this classes.
 */
public abstract class AbstractUseCase {

    protected CassetteRepository cassetteRepository = new CassetteDataRepository(DataSourceFactory.getRealmCassetteDataStore());
    protected RecordingRepository recordingRepository
            = new RecordingDataRepository(DataSourceFactory.getRealmRecordingDataStore());

    protected static List<CassetteModel> CassetteModelList = DataHolderSingleton.getCassetteModelList();

}
