package nd.rw.cassetteui.app.presenter.dp;

import android.util.Log;

import com.annimon.stream.Stream;

import java.util.LinkedList;
import java.util.List;

import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.presenter.ListCassettePresenter;

/**
 * Serves as the singleton subject in Observable design pattern.
 * Aggregates all ListCassettePresenters upon their creation which then allows the subject to notify
 * them of any incoming messages from ActionPresenter such as {Add/Update/Delete}CassettePresenter or
 * {Add/Update/Delete}RecordingPresenter.
 */
public class ListCassettePresenterSubject {

    //region Fields
    private static String TAG = "ListSubject";
    private static ListCassettePresenterSubject ourInstance = new ListCassettePresenterSubject();
    private List<ListCassettePresenter> listPresenters = new LinkedList<>();

    //endregion Fields
    private ListCassettePresenterSubject() {

    }

    public void attach(ListCassettePresenter presenter){
        Log.d(TAG, "attach");
        listPresenters.add(presenter);
    }

    public void detach(ListCassettePresenter presenter){
        listPresenters.remove(presenter);
    }

    public void notifyAboutAddedCassette(CassetteModel cassette){
        Log.d(TAG, "notifyAboutAddedCassette");
        Stream.of(listPresenters).forEach(presenter -> presenter.onAddCassette(cassette));
    }

    public void notifyAboutUpdateCassette(CassetteModel cassette){
        Log.d(TAG, "notifyAboutUpdateCassette");
        Stream.of(listPresenters).forEach(presenter -> presenter.onUpdateCassette(cassette));
    }

    public void notifyAboutDeletedCassete(CassetteModel cassetteModel){
        Log.d(TAG, "notifyAboutDeletedCassete");
        Stream.of(listPresenters).forEach(presenter -> presenter.onDeleteCassette(cassetteModel));
    }

    public static ListCassettePresenterSubject getInstance() {
        return ourInstance;
    }
}
