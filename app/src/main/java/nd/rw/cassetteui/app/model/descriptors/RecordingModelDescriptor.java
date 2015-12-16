package nd.rw.cassetteui.app.model.descriptors;

import nd.rw.cassetteui.app.model.RecordingModel;

public class RecordingModelDescriptor {

    public String title;
    public String transcription;
    public String dateRecorded;
    public String duration;

    public RecordingModelDescriptor(RecordingModel recordingModel) {
        this.title = recordingModel.title;
        this.transcription = recordingModel.transcription;
        this.dateRecorded = NiceDateFormatter.getNiceDate(recordingModel.dateRecorded);
        this.duration = DurationFormatter.formatTimeInSeconds(recordingModel.getDurationInSeconds());
    }
}
