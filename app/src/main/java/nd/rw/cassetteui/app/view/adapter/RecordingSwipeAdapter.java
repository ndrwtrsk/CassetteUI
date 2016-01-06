package nd.rw.cassetteui.app.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.listeners.bundles.RecordingListenerBundle;
import nd.rw.cassetteui.app.model.RecordingModel;
import nd.rw.cassetteui.app.model.descriptors.RecordingModelDescriptor;

public class RecordingSwipeAdapter
        extends RecyclerSwipeAdapter<RecordingSwipeAdapter.RecordingSwipeViewHolder>{

    private static final String TAG = "RecordingSwipeAdapter";
    private List<RecordingModel> recordingList;
    private RecordingListenerBundle recordingListenerBundle;

    public RecordingSwipeAdapter(List<RecordingModel> recordingModelList, RecordingListenerBundle listenerBundle) {
        this.recordingList = recordingModelList;
        this.recordingListenerBundle = listenerBundle;
    }

    @Override
    public RecordingSwipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_recording_item, parent, false);
        return new RecordingSwipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordingSwipeViewHolder viewHolder, int position) {
        RecordingModel recording = recordingList.get(position);
        viewHolder.bind(recording);
        viewHolder.bindListeners(recording, this.recordingListenerBundle);
        viewHolder.sl_Layout.setShowMode(SwipeLayout.ShowMode.PullOut);
        viewHolder.sl_Layout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.ll_bottom_wrapper);
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

    public void deleteRecording(RecordingModel recording){
        for (int i = 0; i < recordingList.size(); i++) {
            RecordingModel currentRecording = recordingList.get(i);
            if (currentRecording.id == recording.id){
                recordingList.remove(i);
                notifyItemRemoved(i);
                return;
            }
        }
        Log.i(TAG, "deleteRecording: No recording was removed.");
    }

    /**
     * Adds to top.
     */
    public void addRecording(RecordingModel recording){
        if (recordingList.size() == 0){
            recordingList.add(recording);
        } else {
            recordingList.add(0, recording);
        }
        notifyItemInserted(0);

    }

    public void updateRecording(RecordingModel recording){

    }

    public static class RecordingSwipeViewHolder extends RecyclerView.ViewHolder{

        private static final String TAG = "RecordingViewHolder";
        @Bind(R.id.list_view_recording_title)
        public TextView tv_title;

        @Bind(R.id.list_view_recording_duration)
        public TextView tv_duration;

        @Bind(R.id.list_view_recording_recorded)
        public TextView tv_recordedOn;

        @Bind(R.id.play_stop_button)
        public ImageButton ib_play_stop_button;

        @Bind(R.id.bottom_wrapper)
        public LinearLayout ll_bottom_wrapper;

        @Bind(R.id.swipe_button_delete_recording)
        public ImageButton ib_delete_recording;

        @Bind(R.id.swipe_layout_recording)
        public  SwipeLayout sl_Layout;

        public RecordingSwipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(RecordingModel recording){
            if (recording == null) {
                Log.e(TAG, "bind: Recording is null");
            }

            RecordingModelDescriptor descriptor = recording.getDescriptor();

            String title = TextUtils.isEmpty(descriptor.title) ? "Recording" : descriptor.title;
            String date = descriptor.dateRecorded;
            String duration = descriptor.duration;

            tv_title.setText(title);
            tv_recordedOn.setText(date);
            tv_duration.setText(duration);
        }

        public void bindListeners(RecordingModel recording, RecordingListenerBundle bundle){
            this.ib_play_stop_button.setOnClickListener(v ->
                    bundle.getOnRecordingClickedHandler().onRecordingClicked(recording));
            ib_delete_recording.setOnClickListener(v ->
                    bundle.getOnRecordingDeleteClickedHandler().onRecordingDeleteClicked(recording));
        }
    }
}
