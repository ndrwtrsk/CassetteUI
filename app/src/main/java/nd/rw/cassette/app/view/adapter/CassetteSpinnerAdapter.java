package nd.rw.cassette.app.view.adapter;

import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import nd.rw.cassette.R;
import nd.rw.cassette.app.model.CassetteModel;

public class CassetteSpinnerAdapter implements SpinnerAdapter {

    //region Fields

    private static final String TAG = "CassSpinnerAdapter";

    private List<CassetteModel> cassetteModelList;

    //endregion Fields

    public CassetteSpinnerAdapter(List<CassetteModel> cassetteModelList) {
        this.cassetteModelList = cassetteModelList;
    }

    //region Methods

    public void setCassetteModelList(List<CassetteModel> cassetteModelList) {
        this.cassetteModelList = cassetteModelList;
    }

    public void addCassette(CassetteModel cassetteModel){
        if (cassetteModelList == null || cassetteModel == null) {
            return;
        }
        if (cassetteModelList.size() == 0){
            cassetteModelList.add(cassetteModel);
        } else {
            cassetteModelList.add(0, cassetteModel);
        }

    }

    public void updateCassette(CassetteModel cassetteModel){
        if (cassetteModelList == null || cassetteModel == null) {
            return;
        }

        for (int i = 0; i < cassetteModelList.size(); i++) {
            if (cassetteModelList.get(i).id == cassetteModel.id){
                cassetteModelList.set(i, cassetteModel);
                return;
            }
        }
        Log.d(TAG, "updateCassette: No Cassette was updated.");
    }

    public void deleteCassette(CassetteModel cassetteModel){
        if (cassetteModelList == null || cassetteModel == null) {
            return;
        }

        this.deleteCassette(cassetteModel.id);

    }

    public void deleteCassette(int cassetteId){
        for (int i = 0; i < cassetteModelList.size(); i++) {
            if (cassetteModelList.get(i).id == cassetteId){
                cassetteModelList.remove(i);
                return;
            }
        }
        Log.d(TAG, "deleteCassette: No Cassette was removed.");
    }


    //endregion Methods

    //region SpinnerAdapter Methods

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.spinner_cassette_listed_item, parent, false);
        }

        CassetteModel cassetteModel = (CassetteModel) getItem(position);

        TextView tv_cassetteTitle = (TextView) convertView.findViewById(R.id.cassette_title);
        tv_cassetteTitle.setText(cassetteModel.title);

        return convertView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        if (cassetteModelList == null)
            return 0;
        else
            return cassetteModelList.size();
    }

    @Override
    public Object getItem(int position) {
        if (cassetteModelList == null){
            return null;
        }
        return cassetteModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (cassetteModelList == null) {
            return -1;
        }
        return cassetteModelList.get(position).id;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CassetteModel cassetteModel = (CassetteModel) getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.spinner_cassette_selected_item, parent, false);
        }

        TextView tv_cassetteTitle = (TextView) convertView.findViewById(R.id.cassette_title);
        tv_cassetteTitle.setText(cassetteModel.title);

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        if (cassetteModelList == null)
            return true;
        else
            return cassetteModelList.isEmpty();
    }

    //endregion SpinnerAdapter Methods

}
