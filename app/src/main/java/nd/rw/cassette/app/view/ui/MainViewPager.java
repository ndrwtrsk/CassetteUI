package nd.rw.cassette.app.view.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import nd.rw.cassette.app.listeners.MainViewPagerMotionBlocker;

public class MainViewPager extends ViewPager {

    private MainViewPagerMotionBlocker motionBlocker;

    public MainViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMotionBlocker(MainViewPagerMotionBlocker motionBlocker) {
        this.motionBlocker = motionBlocker;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!this.motionBlocker.blockOrNot()) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!this.motionBlocker.blockOrNot()) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

}
