package nd.rw.cassetteui.app.view.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import nd.rw.cassetteui.app.CassetteApplication;
import nd.rw.cassetteui.app.di.components.ApplicationComponent;
import nd.rw.cassetteui.app.di.modules.ActivityModule;
import nd.rw.cassetteui.app.navigation.Navigator;

public abstract class BaseActivity extends AppCompatActivity {

    //region Fields

    @Inject
    protected Navigator navigator;

    //endregion Fields

    /**
     * Get the Main Application component for dependency injection.
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((CassetteApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
