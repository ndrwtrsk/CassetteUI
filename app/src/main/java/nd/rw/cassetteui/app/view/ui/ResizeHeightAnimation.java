package nd.rw.cassetteui.app.view.ui;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Courtesy of https://gist.github.com/rafali/5146957
 */
public class ResizeHeightAnimation extends Animation {
    final int startHeight;
    final int targetHeight;
    View view;

    public ResizeHeightAnimation(View view, int targetWidth) {
        this.view = view;
        this.targetHeight = targetWidth;
        startHeight = view.getHeight();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int newWidth = (int) (startHeight + (targetHeight - startHeight) * interpolatedTime);
        view.getLayoutParams().height = newWidth;
        view.requestLayout();
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}
