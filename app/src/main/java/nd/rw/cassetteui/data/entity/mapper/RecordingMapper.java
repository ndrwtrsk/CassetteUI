package nd.rw.cassetteui.data.entity.mapper;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.model.RecordingModel;
import nd.rw.cassetteui.data.entity.RecordingEntity;

public final class RecordingMapper {

    public static RecordingModel map(RecordingEntity recordingEntity){
        if (recordingEntity == null) {
            return null;
        }

        int id = recordingEntity.getId();
        String title = recordingEntity.getTitle();
        String transcription = recordingEntity.getTranscription();
        GregorianCalendar date = new GregorianCalendar();
        date.setTime(recordingEntity.getDateRecorded());
        int duration = recordingEntity.getDurationInMs();
        String path = recordingEntity.getPath();
        CassetteModel cassette = CassetteMapper.mapWithoutRecordings(recordingEntity.getCassette());

        RecordingModel recordingModel = new RecordingModel(id, title, transcription, date, duration, path, cassette);

        return recordingModel;
    }

    public static List<RecordingModel> map(Collection<RecordingEntity> recordingEntities){
        if (recordingEntities == null) {
            return new LinkedList<RecordingModel>();
        }
        LinkedList<RecordingModel> resultList = new LinkedList<>();

        for (RecordingEntity recordingEntity :
                recordingEntities) {
            RecordingModel recordingModel = map(recordingEntity);
            resultList.add(recordingModel);
        }

        return resultList;
    }
}
