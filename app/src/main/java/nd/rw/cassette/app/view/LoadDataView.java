package nd.rw.cassette.app.view;

import android.content.Context;

/**
 * Interface representing a View that will use to load data.
 */
public interface LoadDataView {

    /**
     * Show an error message
     *
     * @param message A string representing an error.
     */
    void showError(String message);

    /**
     * Get a {@link android.content.Context}.
     */
    Context getContext();
}

