package nd.rw.cassetteui.app.view.adapter.layoutmanagers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

public class CassetteLayoutManager extends LinearLayoutManager {

    public CassetteLayoutManager(Context context) {
        super(context);
    }

    public CassetteLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }
}
