package nd.rw.cassette.app.presenter;

import android.util.Log;

import java.util.GregorianCalendar;

import nd.rw.cassette.app.model.CassetteModel;
import nd.rw.cassette.app.model.RecordingModel;
import nd.rw.cassette.app.presenter.dp.ListCassettePresenterSubject;
import nd.rw.cassette.domain.usecase.RecordToCassetteUseCase;

public class NewRecordingPresenter implements Presenter{

    private static final String TAG = "RecordPresenter";
    private CassetteModel selectedCassette;
    private RecordToCassetteUseCase useCase = new RecordToCassetteUseCase();


    public void setSelectedCassette(CassetteModel selectedCassette) {
        this.selectedCassette = selectedCassette;
    }

    public String getCassetteTitle(){
        if (selectedCassette == null) {
            throw new RuntimeException("Cassette was not set prior to getting the title!");
        }
        return selectedCassette.title;
    }

    public RecordingModel addNewRecording(String fileName, GregorianCalendar date, int duration){
        Log.d(TAG, "addNewRecording() called with: " + "fileName = [" + fileName + "], creationDate = [" + date + "], duration = [" + duration + "]");
        RecordingModel newRecording = new RecordingModel(date, duration, fileName, selectedCassette);
        newRecording = useCase.addRecording(newRecording);
        ListCassettePresenterSubject.getInstance().notifyAboutUpdatedCassette(selectedCassette);
        return newRecording;
    }

    //region Presenter Methods
    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
    //endregion Presenter Methods
}