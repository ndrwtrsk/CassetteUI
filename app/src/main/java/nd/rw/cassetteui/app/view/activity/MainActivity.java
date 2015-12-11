package nd.rw.cassetteui.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.listeners.OnCassetteClickedHandler;
import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.navigation.Navigator;
import nd.rw.cassetteui.app.view.fragment.ListCassetteFragment;
import nd.rw.cassetteui.app.view.fragment.RecordingFragment;

public class MainActivity extends BaseActivity implements OnCassetteClickedHandler{

    //region Fields

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private static final int RECORDING_FRAGMENT_SECTION_INDEX = 0;
    private static final int LIST_CASSETTE_SECTION_INDEX = 1;
    private static final String TAG = "MAIN_ACT";

    public RecordingFragment recordingFragment;
    public ListCassetteFragment listCassetteFragment;

    //endregion Fields

    //region AppCompatActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //enable window content transition
        this.getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        recordingFragment = RecordingFragment.newInstance();
        listCassetteFragment = ListCassetteFragment.newInstance();
        listCassetteFragment.setOnCassetteClicked(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion AppCompatActivity

    //region Listeners and events

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mViewPager.getCurrentItem() != RECORDING_FRAGMENT_SECTION_INDEX)
            return super.onKeyDown(keyCode, event);
        else
            return recordingFragment.processKeyEvent(true, keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mViewPager.getCurrentItem() != RECORDING_FRAGMENT_SECTION_INDEX)
            return super.onKeyDown(keyCode, event);
        else
            return recordingFragment.processKeyEvent(false, keyCode, event);
    }

    @Override
    public void onCassetteClicked(CassetteModel cassetteModel, View cassetteViewForTransition) {
//        Log.d(TAG, "onCassetteClicked: cassetteModel.Id: " + cassetteModel.getId());
//        Log.d(TAG, "onCassetteClicked: cassetteViewForTransition == null?: " + (cassetteViewForTransition == null));
//        String transitionName = cassetteViewForTransition.getTransitionName();
//        Log.d(TAG, "onCassetteClicked: transitionName: " + transitionName);
//        Pair participants = new Pair<>(cassetteViewForTransition, transitionName);

//        ActivityOptionsCompat transitionActivityOptions =
//                ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        MainActivity.this, participants);

        Intent intent = DetailCassetteActivity.getCallingIntent(this, cassetteModel.getId());

//        ActivityCompat.startActivity(MainActivity.this, intent, transitionActivityOptions.toBundle());

        this.startActivity(intent);
    }

    //endregion Listeners and events

    //region SectionsPagerAdapter inner class

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case RECORDING_FRAGMENT_SECTION_INDEX:
                    return recordingFragment;
                case LIST_CASSETTE_SECTION_INDEX:
                    return listCassetteFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case RECORDING_FRAGMENT_SECTION_INDEX:
                    return "RECORD";
                case LIST_CASSETTE_SECTION_INDEX:
                    return "CASSETTES";
            }
            return null;
        }
    }

    //endregion SectionsPagerAdapter inner class
}
