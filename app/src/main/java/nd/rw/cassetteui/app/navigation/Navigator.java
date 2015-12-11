package nd.rw.cassetteui.app.navigation;

import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;
import javax.inject.Singleton;

import nd.rw.cassetteui.app.view.activity.DetailCassetteActivity;

/**
 * Application-wide Navigator which provides means of starting activities.
 */
public class Navigator {

    public void Navigator() {
        //empty
    }

    /**
     * Navigates to the details of the Cassette of specified identifier from the provided Context.
     */
    public static void navigateToCassetteDetails(Context context, int cassetteId) {
        if (context == null) {
            return;
        }

        Intent intent = DetailCassetteActivity.getCallingIntent(context, cassetteId);
        context.startActivity(intent);
    }


}

