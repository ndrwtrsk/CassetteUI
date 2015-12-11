package nd.rw.cassetteui.app.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import nd.rw.cassetteui.app.navigation.Navigator;

public abstract class BaseActivity extends AppCompatActivity {

    //region Fields

    protected Navigator navigator;

    //endregion Fields


    //region Methods

    protected void addFragment(int containerViewId, Fragment fragment){
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.add(containerViewId, fragment);
        transaction.commit();

    }

    //endregion Methods

}
