package nd.rw.cassetteui.app.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.listeners.OnCassetteClickedHandler;
import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.model.descriptors.CassetteModelDescriptor;

public class CassettesAdapter extends RecyclerView.Adapter<CassettesAdapter.CassetteViewHolder>{

    //region Fields
    private static final String TAG = "CassettesAdapter";

    private List<CassetteModel> cassetteModelList;
    private OnCassetteClickedHandler onCassetteClickedHandler;

    //endregion Fields

    public CassettesAdapter(List<CassetteModel> cassetteModelList,
                            OnCassetteClickedHandler onCassetteClickedHandler) {
        this.cassetteModelList = cassetteModelList;
        this.onCassetteClickedHandler = onCassetteClickedHandler;
    }

    //region Methods

    public void setCassetteModelList(Collection<CassetteModel> cassetteModelList) {
        this.cassetteModelList = (List<CassetteModel>) cassetteModelList;
    }

    public void addCassetteToTop(CassetteModel cassetteModel){
        if (cassetteModelList == null || cassetteModel == null) {
            return;
        }
        if (cassetteModelList.size() == 0){
            cassetteModelList.add(cassetteModel);
        } else {
            cassetteModelList.add(0, cassetteModel);
        }
        notifyItemInserted(0);
    }

    public void updateCassette(CassetteModel cassetteModel){
        if (cassetteModelList == null || cassetteModel == null) {
            return;
        }

        for (int i = 0; i < cassetteModelList.size(); i++) {
            if (cassetteModelList.get(i).getId() == cassetteModel.getId()){
                cassetteModelList.set(i, cassetteModel);
                notifyItemChanged(i);
                return;
            }
        }
        Log.d(TAG, "updateCassette: No Cassette was updated.");
    }

    public void deleteCassette(CassetteModel cassetteModel){
        if (cassetteModelList == null || cassetteModel == null) {
            return;
        }

        this.deleteCassette(cassetteModel.getId());

    }

    public void deleteCassette(int cassetteId){
        for (int i = 0; i < cassetteModelList.size(); i++) {
            if (cassetteModelList.get(i).getId() == cassetteId){
                cassetteModelList.remove(i);
                notifyItemRemoved(i);
                return;
            }
        }
        Log.d(TAG, "deleteCassette: No Cassette was removed.");
    }

    //endregion Methods

    //region RecyclerView implemented methods

    @Override
    public CassetteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_cassette, parent, false);
        return new CassetteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CassetteViewHolder holder, int position) {
        CassetteModel cassetteAtPosition = this.cassetteModelList.get(position);
        holder.fill(cassetteAtPosition);
        holder.bindListener(cassetteAtPosition, onCassetteClickedHandler);
    }

    @Override
    public int getItemCount() {
        return this.cassetteModelList.size();
    }

    //endregion RecyclerView implemented methods

    //region CassetteViewHolder class

    static class CassetteViewHolder extends RecyclerView.ViewHolder{

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

        public void fill(CassetteModel cassetteModel){
            if (cassetteModel == null) {
                return;
            }
            CassetteModelDescriptor descriptor = cassetteModel.getDescriptor();

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
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handler.onCassetteClicked(cassette, CassetteViewHolder.this.tv_title);
                }
            });
        }
    }

    //endregion CassetteViewHolder class
}
