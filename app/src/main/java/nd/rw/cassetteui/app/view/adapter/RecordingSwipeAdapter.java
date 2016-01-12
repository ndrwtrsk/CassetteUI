package nd.rw.cassetteui.app.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.listeners.RecordingItemHandler;
import nd.rw.cassetteui.app.model.RecordingModel;
import nd.rw.cassetteui.app.model.descriptors.RecordingModelDescriptor;

public class RecordingSwipeAdapter
        extends RecyclerSwipeAdapter<RecordingSwipeAdapter.RecordingSwipeViewHolder>{

    private static final String TAG = "RecordingSwipeAdapter";
    private List<RecordingModel> recordingList;
    private RecordingItemHandler recordingItemHandler;

    public RecordingSwipeAdapter(List<RecordingModel> recordingModelList, RecordingItemHandler recordingItemHandler) {
        this.recordingList = recordingModelList;
        this.recordingItemHandler = recordingItemHandler;

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
        viewHolder.bindListeners(recording, this.recordingItemHandler);
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

        //region Fields

        private static final String TAG = "RecordingViewHolder";
        @Bind(R.id.list_view_recording_title)
        public TextView tv_title;

        /*
                TODO: 09.01.2016 Refactor Duration and duration left names
                right duration points to duration left
                and duration left to duration...

         */

        @Bind(R.id.list_view_recording_duration)
        public TextView tv_duration;

        @Bind(R.id.list_view_recording_duration_left)
        public TextView tv_durationLeft;

        @Bind(R.id.list_view_recording_current_progress)
        public TextView tv_currentProgress;

        @Bind(R.id.list_view_recording_recorded)
        public TextView tv_recordedOn;

        @Bind(R.id.play_stop_button)
        public ImageButton ib_play_stop_button;

        @Bind(R.id.bottom_wrapper)
        public LinearLayout ll_bottom_wrapper;

        @Bind(R.id.swipe_button_delete_recording)
        public ImageButton ib_delete_recording;

        @Bind(R.id.list_view_recording_layout)
        public RelativeLayout rl_surfaceView;

        @Bind(R.id.swipe_layout_recording)
        public SwipeLayout sl_Layout;

        @Bind(R.id.rl_playback)
        public RelativeLayout rl_playback;

        @Bind(R.id.seek_bar)
        public SeekBar sb_progress;

        private boolean isExpanded = false;
        private boolean isGreyedOut = false;
        private String duration;
        //endregion Fields

        public RecordingSwipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(RecordingModel recording){
            if (recording == null) {
                Log.e(TAG, "bind: Recording is null");
                return;
            }

            RecordingModelDescriptor descriptor = recording.getDescriptor();

            String title = TextUtils.isEmpty(descriptor.title) ? "Recording" : descriptor.title;
            String date = descriptor.dateRecorded;
            String duration = descriptor.duration;
            this.duration = duration;

            tv_title.setText(title);
            tv_recordedOn.setText(date);
            tv_duration.setText("-" + duration);
            tv_durationLeft.setText(duration);
        }

        public void bindListeners(RecordingModel recording, RecordingItemHandler handler){
            ib_play_stop_button.setOnClickListener(v -> handler.playClicked(recording));
            ib_delete_recording.setOnClickListener(v -> handler.deleteClicked(recording));
            itemView.setOnClickListener(v -> handler.recordingItemClicked(recording, this));
        }

        public void toggleExpansion(){
            ViewGroup.LayoutParams params = sl_Layout.getLayoutParams();
            if (isExpanded){
                params.height = params.height/2;
            } else {
                params.height = 2 * params.height;
            }
            sl_Layout.setLayoutParams(params);
            toggleHiddenViews();
            isExpanded = !isExpanded;
        }

        private void toggleHiddenViews(){
            int visibility1 = !isExpanded ? View.VISIBLE : View.GONE;
            int visibility2 = !isExpanded ? View.GONE : View.VISIBLE;
            tv_currentProgress.setVisibility(visibility1);
            tv_duration.setVisibility(visibility1);
            rl_playback.setVisibility(visibility1);
            tv_durationLeft.setVisibility(visibility2);
        }

        public void reset(){
            sb_progress.setProgress(0);
            tv_currentProgress.setText("00:00");
            tv_duration.setText("-" + duration);
        }

        public void toggleGreyedOut(){
            if (isGreyedOut){
                rl_surfaceView.setBackgroundColor(0x727272);
            } else {
                rl_surfaceView.setBackgroundColor(0xFFFFFF);
            }
            isGreyedOut = !isGreyedOut;
        }
    }
}
