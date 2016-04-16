package nd.rw.cassette.app.view.adapter;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.cassette.R;
import nd.rw.cassette.app.model.CassetteModel;
import nd.rw.cassette.app.model.descriptors.CassetteModelDescriptor;

public class CassettesAdapter extends RecyclerView.Adapter<CassettesAdapter.CassetteViewHolder>{

    //region Fields

    private static final String TAG = "CassettesAdapter";

    private CassetteSortedListCallback cassetteSortedListCallback;
    private SortedList<CassetteModel> cassetteSortedList;
    private CassetteViewHolder.OnCassetteClickedHandler onCassetteClickedHandler;

    //endregion Fields

    public CassettesAdapter(CassetteViewHolder.OnCassetteClickedHandler onCassetteClickedHandler) {
        cassetteSortedListCallback = new CassetteSortedListCallback(this);
        cassetteSortedList = new SortedList<>(CassetteModel.class, cassetteSortedListCallback);
        this.onCassetteClickedHandler = onCassetteClickedHandler;
    }

    //region Methods

    public void setCassetteSortedList(Collection<CassetteModel> cassetteModelList) {
        this.cassetteSortedList.addAll(cassetteModelList);
    }

    public void addCassetteToTop(CassetteModel cassette){
        if (cassetteSortedList == null || cassette == null) {
            return;
        }

        cassetteSortedList.add(cassette);
    }

    public void updateCassette(CassetteModel cassette){
        if (cassetteSortedList == null || cassette == null) {
            return;
        }

        int foundCassetteIndex = this.cassetteSortedList.indexOf(cassette);
        if (foundCassetteIndex == SortedList.INVALID_POSITION){
            Log.d(TAG, "updateCassette: No Cassette was updated.");
            return;
        }
        this.cassetteSortedList.updateItemAt(foundCassetteIndex, cassette);
        Log.d(TAG, "updateCassette: Cassette was successfully updated.");
    }

    public void deleteCassette(CassetteModel cassette){
        if (cassetteSortedList == null || cassette == null) {
            return;
        }
        Log.d(TAG, "deleteCassette() called with: " + " searched cassette = [" + cassette + "]");

        for (int i = 0; i < cassetteSortedList.size(); i++) {
            if (cassetteSortedList.get(i).id == cassette.id) {
                cassetteSortedList.removeItemAt(i);
                break;
            }
        }
    }

    //endregion Methods

    //region RecyclerView implemented methods

    @Override
    public CassetteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_cassette_item, parent, false);
        return new CassetteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CassetteViewHolder holder, int position) {
        CassetteModel cassetteAtPosition = this.cassetteSortedList.get(position);
        holder.bind(cassetteAtPosition);
        holder.bindListener(cassetteAtPosition, onCassetteClickedHandler);
    }

    @Override
    public int getItemCount() {
        return this.cassetteSortedList.size();
    }

    //endregion RecyclerView implemented methods

    //region CassetteViewHolder class

    public static class CassetteViewHolder extends RecyclerView.ViewHolder{

        public interface OnCassetteClickedHandler {

            void onCassetteClicked(CassetteModel cassetteModel, View cassetteViewForTransition);

        }

        //region Fields

        @Bind(R.id.card_view_and_details_cassette_title)
        public TextView tv_title;

        @Bind(R.id.list_item_cassette_description)
        public TextView tv_description;

        @Bind(R.id.list_item_cassette_no_of_recordings)
        public TextView tv_numberOfRecordings;

        @Bind(R.id.list_item_cassette_duration)
        public TextView tv_totalDuration;

        //endregion Fields

        public CassetteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(CassetteModel cassette){
            if (cassette == null) {
                return;
            }
            CassetteModelDescriptor descriptor = cassette.getDescriptor();

            this.tv_title.setText(descriptor.title);
            this.tv_description.setText(descriptor.description);
            String dateLastRecorded;
            if (descriptor.dateOfLastRecording == null) {
                dateLastRecorded = "No recordings";
            } else {
                dateLastRecorded = descriptor.dateOfLastRecording;
            }
            this.tv_numberOfRecordings.setText(dateLastRecorded);
            this.tv_totalDuration.setText(descriptor.totalDurationOfAllRecordings);
        }

        public void bindListener(final CassetteModel cassette, final OnCassetteClickedHandler handler){
            this.itemView.setOnClickListener(view -> handler.onCassetteClicked(cassette, CassetteViewHolder.this.tv_title));
        }
    }

    //endregion CassetteViewHolder class

    //region CassetteSortedListCallback class

    private static class CassetteSortedListCallback extends SortedListAdapterCallback<CassetteModel>{

        public CassetteSortedListCallback(RecyclerView.Adapter adapter) {
            super(adapter);
        }

        @Override
        public int compare(CassetteModel o1, CassetteModel o2) {
            return o1.compareTo(o2);
        }

        @Override
        public boolean areContentsTheSame(CassetteModel oldItem, CassetteModel newItem) {
            return oldItem.title.equalsIgnoreCase(newItem.title)
                    && oldItem.description.equalsIgnoreCase(newItem.description)
                    && oldItem.getNumberOfRecordings() == newItem.getNumberOfRecordings();
        }

        @Override
        public boolean areItemsTheSame(CassetteModel item1, CassetteModel item2) {
            return item1.id == item2.id;
        }
    }

    //endregion CassetteSortedListCallback class
}
