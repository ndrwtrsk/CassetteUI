package nd.rw.cassetteui.app.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.model.RecordingModel;
import nd.rw.cassetteui.app.model.descriptors.RecordingModelDescriptor;

public class RecordingViewHolder extends RecyclerView.ViewHolder{

    //region Fields

    private static final String TAG = "RecordingViewHolder";
    @Bind(R.id.list_view_recording_title)
    public TextView tv_title;

    @Bind(R.id.list_view_recording_duration)
    public TextView tv_duration;

    @Bind(R.id.list_view_recording_recorded)
    public TextView tv_recordedOn;

   /* @Bind(R.id.list_view_recording_more)
    public ImageButton iv_more;*/

    //endregion Fields

    public RecordingViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
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
}
