package nd.rw.cassette.app.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {

    //region Methods

    protected void addFragment(int containerViewId, Fragment fragment){
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.add(containerViewId, fragment);
        transaction.commit();

    }

    //endregion Methods

}
