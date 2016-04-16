package nd.rw.cassette.app.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import nd.rw.cassette.R;
import nd.rw.cassette.app.view.fragment.ListCassetteFragment;
import nd.rw.cassette.app.view.fragment.RecordingFragment;
import nd.rw.cassette.app.view.ui.RecordingMotionBlockingViewPager;

public class MainActivity
        extends BaseActivity {

    //region Fields

    private SectionsPagerAdapter mSectionsPagerAdapter;

    public RecordingMotionBlockingViewPager mViewPager;

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

        mViewPager = (RecordingMotionBlockingViewPager) findViewById(R.id.container);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        recordingFragment = RecordingFragment.newInstance();
        listCassetteFragment = ListCassetteFragment.newInstance();
        mViewPager.setMotionBlocker(recordingFragment);
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
