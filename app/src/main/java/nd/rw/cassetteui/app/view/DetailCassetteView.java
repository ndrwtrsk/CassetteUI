package nd.rw.cassetteui.app.view;


import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.model.RecordingModel;

public interface DetailCassetteView extends LoadDataView {
    void renderCassetteAndRecordings(CassetteModel cassetteModel);
    void refreshTitleAndDescription(CassetteModel cassetteModel);
    void addUndidDeleteRecording(RecordingModel recordingModel);
}
