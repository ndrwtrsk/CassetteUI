package nd.rw.cassetteui.app.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.model.CassetteModel;

public class CassettesAdapter extends RecyclerView.Adapter<CassettesAdapter.CassetteViewHolder>{

    //region Fields

    private List<CassetteModel> cassetteModelList;

    //endregion Fields

    public CassettesAdapter(List<CassetteModel> cassetteModelList) {
        this.cassetteModelList = cassetteModelList;
    }

    public void setCassetteModelList(Collection<CassetteModel> cassetteModelList) {
        this.cassetteModelList = (List<CassetteModel>) cassetteModelList;
    }


    //region RecyclerView implemented methods

    @Override
    public CassetteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.card_view_cassette, parent);

        return new CassetteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CassetteViewHolder holder, int position) {
        CassetteModel cassetteAtPosition = this.cassetteModelList.get(position);
        holder.fill(cassetteAtPosition);
    }

    @Override
    public int getItemCount() {
        return this.cassetteModelList.size();
    }

    //endregion RecyclerView implemented methods

    //region CassetteViewHolder class

    static class CassetteViewHolder extends RecyclerView.ViewHolder{

        //region Fields

        @Bind(R.id.card_view_cassette_title)
        public TextView tv_title;

        @Bind(R.id.card_view_cassette_description)
        public TextView tv_description;

        @Bind(R.id.card_view_cassette_no_of_recordings)
        public TextView tv_numberOfRecordings;

        @Bind(R.id.card_view_cassette_duration)
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
            this.tv_title.setText(cassetteModel.getTitle());
            this.tv_description.setText(cassetteModel.getDescription());
            this.tv_numberOfRecordings.setText(cassetteModel.getNumberOfRecordings());
            this.tv_totalDuration.setText(cassetteModel.getTotalDuration());
        }
    }

    //endregion CassetteViewHolder class
}
