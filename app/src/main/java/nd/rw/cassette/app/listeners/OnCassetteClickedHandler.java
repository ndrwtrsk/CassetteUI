package nd.rw.cassette.app.listeners;

import android.view.View;

import nd.rw.cassette.app.model.CassetteModel;

public interface OnCassetteClickedHandler {

    void onCassetteClicked(CassetteModel cassetteModel, View cassetteViewForTransition);

}
