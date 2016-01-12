package nd.rw.cassetteui.app.listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import nd.rw.cassetteui.app.view.adapter.RecordingSwipeAdapter;

public class RecordingRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private RecordingSwipeAdapter.RecordingSwipeViewHolder expandedViewHolder;
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
        RecordingSwipeAdapter.RecordingSwipeViewHolder viewHolder
                = (RecordingSwipeAdapter.RecordingSwipeViewHolder) rv.findViewHolderForAdapterPosition(childPosition);

        if (viewHolder != null && gestureDetector.onTouchEvent(e)) {
            if (viewHolder.equals(expandedViewHolder)){
                return false;
            }
            if (expandedViewHolder != null) {
                expandedViewHolder.toggleExpansion();
                expandedViewHolder.reset();
            }
            viewHolder.toggleExpansion();
            expandedViewHolder = viewHolder;
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
