package nd.rw.cassetteui.data.entity.mapper;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.model.RecordingModel;
import nd.rw.cassetteui.data.entity.CassetteEntity;

public final class CassetteMapper {
    
    public static CassetteModel map(CassetteEntity cassetteEntity){
        if (cassetteEntity == null) {
            return null;
        }
        CassetteModel cassetteModel = mapWithoutRecordings(cassetteEntity);
        List<RecordingModel> recordingModelList = RecordingMapper.map(cassetteEntity.getRecordingEntities());
        cassetteModel.setRecordingList(recordingModelList);
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

    public static List<CassetteModel> map(Collection<CassetteEntity> cassetteEntities){
        if (cassetteEntities == null) {
            return new LinkedList<>();
        }
        List<CassetteModel> result = new LinkedList<>();
        for (CassetteEntity cassetteEntity :
                cassetteEntities) {
            CassetteModel cassetteModel = map(cassetteEntity);
            result.add(cassetteModel);
        }
        return result;
    }
}
