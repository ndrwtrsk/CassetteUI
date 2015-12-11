package nd.rw.cassetteui.app.listeners;

import android.view.View;

import nd.rw.cassetteui.app.model.CassetteModel;

public interface OnCassetteClickedHandler {

    void onCassetteClicked(CassetteModel cassetteModel, View cassetteViewForTransition);

}
