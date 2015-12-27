package nd.rw.cassetteui.app.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.listeners.OnRecordingClickedHandler;
import nd.rw.cassetteui.app.model.RecordingModel;

public class RecordingSwipeAdapter
        extends RecyclerSwipeAdapter<RecordingSwipeAdapter.RecordingSwipeViewHolder>{

    private List<RecordingModel> recordingList;
    private OnRecordingClickedHandler recordingClickedHandler;


    public RecordingSwipeAdapter(List<RecordingModel> recordingModelList, OnRecordingClickedHandler handler) {
        this.recordingList = recordingModelList;
        this.recordingClickedHandler = handler;
    }

    @Override
    public RecordingSwipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.swipe_layout_list_recording_item, parent, false);
        return new RecordingSwipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordingSwipeViewHolder viewHolder, int position) {
        RecordingModel recording = recordingList.get(position);
        viewHolder.bind(recording);
        viewHolder.bindListener(recording, this.recordingClickedHandler);
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.ll_bottomWrapper);
    }

    @Override
    public int getItemCount() {
        return recordingList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return 0;
    }

    public void setRecordingList(List<RecordingModel> recordingList) {
        this.recordingList = recordingList;
    }

    private SwipeLayout.SwipeListener swipeListener = new SwipeLayout.SwipeListener() {
        @Override
        public void onStartOpen(SwipeLayout layout) {

        }

        @Override
        public void onOpen(SwipeLayout layout) {

        }

        @Override
        public void onStartClose(SwipeLayout layout) {

        }

        @Override
        public void onClose(SwipeLayout layout) {

        }

        @Override
        public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

        }

        @Override
        public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

        }
    };

    public static class RecordingSwipeViewHolder extends RecyclerView.ViewHolder{

        /**
         * Reutilize previous version of RecoridngViewHolder.
         */
        private RecordingViewHolder recordingViewHolder;

        @Bind(R.id.play_stop_button)
        public ImageButton ib_play_stop_button;

        @Bind(R.id.bottom_wrapper)
        public LinearLayout ll_bottomWrapper;

        @Bind(R.id.swipe_layout_recording)
        public  SwipeLayout swipeLayout;

        public RecordingSwipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            recordingViewHolder = new RecordingViewHolder(itemView);
        }

        public void bind(RecordingModel recording){
            recordingViewHolder.bind(recording);
        }

        public void bindListener(RecordingModel recording, OnRecordingClickedHandler handler){
            ib_play_stop_button.setOnClickListener(v -> handler.onRecordingClickedHandler(recording));
        }
    }
}
