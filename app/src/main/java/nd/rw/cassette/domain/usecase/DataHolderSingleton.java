package nd.rw.cassette.domain.usecase;

import java.util.LinkedList;
import java.util.List;

import nd.rw.cassette.app.model.CassetteModel;
import nd.rw.cassette.app.model.RecordingModel;

/**
 * Basically just contains data such as Cassettes and Recordings.
 * Fakes a database. Or whatever. :)
 */
public class DataHolderSingleton {

    private static List<CassetteModel> cassetteModelList;

    private static List<RecordingModel> recordingModelList;

    public static List<CassetteModel> getCassetteModelList() {
        if (cassetteModelList == null) {
            cassetteModelList = CassetteModel.getListOfCassettes(10, 10);
        }
        return cassetteModelList;
    }

    public static List<RecordingModel> getRecordingModelList() {
        if (recordingModelList == null) {
            recordingModelList = DataHolderSingleton.extractRecordingsFromCassettes();
        }
        return recordingModelList;
    }

    private static List<RecordingModel> extractRecordingsFromCassettes() {
        List<RecordingModel> recordingModels = new LinkedList<>();

        for (CassetteModel cassetteModel :
                cassetteModelList) {
            recordingModels.addAll(cassetteModel.getRecordingList());
        }

        return recordingModels;
    }
}
