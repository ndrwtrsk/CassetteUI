package nd.rw.cassette.app.view;


import nd.rw.cassette.app.model.CassetteModel;
import nd.rw.cassette.app.model.RecordingModel;

public interface DetailCassetteView extends LoadDataView {
    void renderCassetteAndRecordings(CassetteModel cassetteModel);
    void refreshTitleAndDescription(CassetteModel cassetteModel);
    void addRecentlyDeletedRecording(RecordingModel recordingModel);
}
