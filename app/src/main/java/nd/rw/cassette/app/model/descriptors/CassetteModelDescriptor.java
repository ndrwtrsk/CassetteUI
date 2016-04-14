package nd.rw.cassette.app.model.descriptors;

import nd.rw.cassette.app.model.CassetteModel;

/**
 * Provides human readable version of Cassette and it's various fields formatted to do this task
 *
 *
 * just perfectly.
 */
public class CassetteModelDescriptor {

    public String title;
    public String description;
    public String numberOfRecordings;
    public String totalDurationOfAllRecordings;
    public String dateCreated;
    public String dateOfLastRecording;

    public CassetteModelDescriptor(CassetteModel cassetteModel) {
        this.title = cassetteModel.getTitle();
        this.description = cassetteModel.getDescription();

        this.numberOfRecordings = this.getNumberOfRecordingsNicelyFormatted(cassetteModel.getNumberOfRecordings());
        this.totalDurationOfAllRecordings = DurationFormatter.formatTimeInSeconds(cassetteModel.getTotalDuration());

        this.dateCreated = NiceDateFormatter.getNiceDate(cassetteModel.getDate());
        this.dateOfLastRecording = NiceDateFormatter.getNiceDate(cassetteModel.getNewestRecordingDate());
    }

    private String getNumberOfRecordingsNicelyFormatted(int numberOfRecordings){
        if (numberOfRecordings <= 0){
            return "No recordings";
        }

        if (numberOfRecordings == 1){
            return "Just one recording";
        }

        if (numberOfRecordings > 1){
            return numberOfRecordings + " recordings";
        }

        return "lol"; //fixme dont fuck around, seriously
    }



}
