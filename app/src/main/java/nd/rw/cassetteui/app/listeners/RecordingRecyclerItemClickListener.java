package nd.rw.cassetteui.app.listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import nd.rw.cassetteui.app.view.adapter.RecordingSwipeAdapter;

public class RecordingRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private static final String TAG = "RecItemClickListener";
    private RecordingSwipeAdapter.RecordingSwipeViewHolder activeViewHolder;
    private GestureDetector gestureDetector;

    public RecordingRecyclerItemClickListener(Context context) {
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }


    // TODO: 12.01.2016 Move expansion and greying out responibility to RecordinSwipeAdapter
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        int childPosition = rv.getChildAdapterPosition(childView);
        RecordingSwipeAdapter.RecordingSwipeViewHolder newlySelectedViewHolder
                = (RecordingSwipeAdapter.RecordingSwipeViewHolder) rv.findViewHolderForAdapterPosition(childPosition);

        if (newlySelectedViewHolder != null && gestureDetector.onTouchEvent(e)) {

            // TODO: 12.01.2016 Second tap on the same view holder should shrink it and remove greying-out
            //  on rest viewholders

            if (newlySelectedViewHolder.equals(activeViewHolder)){
                return false;
            }
            //  make the currently viewholder inactive
            if (activeViewHolder != null) {
                activeViewHolder.toggleExpansion();
                activeViewHolder.reset();
            }
            newlySelectedViewHolder.toggleExpansion();
            activeViewHolder = newlySelectedViewHolder;
/*
            RecyclerView.Adapter adapter = rv.getAdapter();

            Log.d(TAG, "onInterceptTouchEvent: Iterating over RecyclerView items");

            Log.d(TAG, "onInterceptTouchEvent: adapter item count = ["+adapter.getItemCount()+"]");
            for (int i = 0; i < adapter.getItemCount(); i++) {
                Log.d(TAG, "onInterceptTouchEvent: current index = [" + i + "]");
                RecordingSwipeAdapter.RecordingSwipeViewHolder iteratedViewHolder
                        = (RecordingSwipeAdapter.RecordingSwipeViewHolder) rv.findViewHolderForAdapterPosition(i);
                if (iteratedViewHolder == null) {
                    Log.d(TAG, "onInterceptTouchEvent: view holder is null");
                    continue;
                }
                Log.d(TAG, "onInterceptTouchEvent: Current ViewHolder isActive = [" + iteratedViewHolder.isActive() + "], " +
                        "isGreyedOut = [" + iteratedViewHolder.isGreyedOut() + "]," +
                        "layoutPosition = [" + iteratedViewHolder.getLayoutPosition() + "]" +
                        "adapterPosition = [" + iteratedViewHolder.getAdapterPosition() + "]");
                if (i == childPosition)
                    continue;
                iteratedViewHolder.toggleGreyedOut();
            }
*/
        }


        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
