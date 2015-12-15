package nd.rw.cassetteui.app.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

public class RecordingLayoutManager extends LinearLayoutManager {
    public RecordingLayoutManager(Context context) {
        super(context);
    }

    public RecordingLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public RecordingLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
