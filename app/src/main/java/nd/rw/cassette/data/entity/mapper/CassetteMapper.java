package nd.rw.cassette.data.entity.mapper;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import nd.rw.cassette.app.model.CassetteModel;
import nd.rw.cassette.app.model.RecordingModel;
import nd.rw.cassette.data.entity.CassetteEntity;

public final class CassetteMapper {
    
    public static CassetteModel mapWithRecordings(CassetteEntity cassetteEntity){
        if (cassetteEntity == null) {
            return null;
        }
        CassetteModel cassetteModel = mapWithoutRecordings(cassetteEntity);
        List<RecordingModel> recordingModelList = RecordingMapper.map(cassetteEntity.getRecordingEntities());
        cassetteModel.recordingList = recordingModelList;
        return cassetteModel;
    }

    public static CassetteModel mapWithoutRecordings(CassetteEntity cassetteEntity){
        if (cassetteEntity == null) {
            return null;
        }

        int id = cassetteEntity.getId();
        String title = cassetteEntity.getTitle();
        String descripiton = cassetteEntity.getDescription();
        GregorianCalendar date = new GregorianCalendar();
        date.setTime(cassetteEntity.getDate());

        CassetteModel cassetteModel = new CassetteModel(id, title, descripiton, date);

        return cassetteModel;
    }

    public static List<CassetteModel> mapWithRecordings(Collection<CassetteEntity> cassetteEntities){
        if (cassetteEntities == null) {
            return new LinkedList<>();
        }
        List<CassetteModel> result = new LinkedList<>();
        for (CassetteEntity cassetteEntity :
                cassetteEntities) {
            CassetteModel cassetteModel = mapWithRecordings(cassetteEntity);
            result.add(cassetteModel);
        }
        return result;
    }
}
