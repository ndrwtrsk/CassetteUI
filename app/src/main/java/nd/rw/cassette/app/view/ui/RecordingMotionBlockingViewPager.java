package nd.rw.cassette.app.view.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class RecordingMotionBlockingViewPager extends ViewPager {

    public interface BlockOrNot {

        boolean blockOrNot();

    }

    private BlockOrNot motionBlocker;

    public RecordingMotionBlockingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMotionBlocker(BlockOrNot motionBlocker) {
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
