package nd.rw.cassetteui.app.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.model.RecordingModel;

public class RecordingListViewAdapter extends RecyclerView.Adapter<RecordingViewHolder> {

    //region Fields

    public static final String TAG = "RecordingListViewAdapter";

    private List<RecordingModel> recordingList;

    //endregion Fields

    //region Constructor

    public RecordingListViewAdapter(List<RecordingModel> recordingList) {
        this.recordingList = recordingList;
    }

    //endregion Constructor

    public void setRecordingList(List<RecordingModel> recordingList) {
        this.recordingList = recordingList;
    }

    //region RecyclerView.Adapter Methods

    @Override
    public RecordingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_recording_item, parent, false);
        return new RecordingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordingViewHolder holder, int position) {
        RecordingModel recording = recordingList.get(position);
        holder.bind(recording);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        if (recordingList == null) {
            return 0;
        }
        return recordingList.size();
    }

    //endregion RecyclerView.Adapter Methods
}