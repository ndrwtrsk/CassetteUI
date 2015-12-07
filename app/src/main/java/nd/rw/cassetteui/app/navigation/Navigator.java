package nd.rw.cassetteui.app.navigation;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Application-wide Navigator which provides means of starting activities.
 */
@Singleton
public class Navigator {

    @Inject
    public void Navigator() {
        //empty
    }

    /*
    *//**
     * Navigates to the Cassette list from the provided Context.
     *//*
    public void navigateToCassetteList(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = L.getCallingIntent(context);
        context.startActivity(intent);
    }

    *//**
     * Navigates to the details of the Cassette of specified identifier from the provided Context.
     *//*
    public void navigateToCassetteDetails(Context context, long cassetteId) {
        if (context == null) {
            return;
        }

        Intent intent = CassetteDetailActivity.getCallingIntent(context, cassetteId);
        context.startActivity(intent);
    }
    */

}

