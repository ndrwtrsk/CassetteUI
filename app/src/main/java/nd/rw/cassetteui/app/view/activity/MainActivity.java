package nd.rw.cassetteui.app.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import javax.inject.Inject;

import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.di.components.CassetteComponent;
import nd.rw.cassetteui.app.view.fragment.ListCassetteFragment;
import nd.rw.cassetteui.app.view.fragment.RecordingFragment;

public class MainActivity extends BaseActivity {

    //region Fields

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Inject
    private RecordingFragment recordingFragment;

    @Inject
    private ListCassetteFragment listCassetteFragment;

    private CassetteComponent cassetteComponent;

    //endregion Fields

    //region AppCompatActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        if (mViewPager.getCurrentItem() != 0)
            return super.onKeyDown(keyCode, event);
        else
            return recordingFragment.processKeyEvent(true, keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mViewPager.getCurrentItem() != 0)
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
            if (position == 0){
                return recordingFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "RECORD";
                case 1:
                    return "YOUR CASSETTES";
            }
            return null;
        }
    }

    //endregion SectionsPagerAdapter inner class
}
