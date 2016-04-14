package nd.rw.cassette.app.view.ui;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

public class RecordingButtonShadow extends ViewOutlineProvider {

    //region Constructors

    //endregion Constructors

    /**
     * Called to get the provider to populate the Outline.
     * <p>
     * This method will be called by a View when its owned Drawables are invalidated, when the
     * View's size changes, or if {@link View#invalidateOutline()} is called
     * explicitly.
     * <p>
     * The input outline is empty and has an alpha of <code>1.0f</code>.
     *
     * @param view    The view building the outline.
     * @param outline The empty outline to be populated.
     */
    @Override
    public void getOutline(View view, Outline outline) {
        
    }

}
