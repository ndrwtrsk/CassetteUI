package nd.rw.cassetteui.app.view.adapter;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.model.CassetteModel;

public class CassetteSpinnerAdapter implements SpinnerAdapter {
    List<CassetteModel> cassetteModelList;

    public CassetteSpinnerAdapter(List<CassetteModel> cassetteModelList) {
        this.cassetteModelList = cassetteModelList;
    }

    public void setCassetteModelList(List<CassetteModel> cassetteModelList) {
        this.cassetteModelList = cassetteModelList;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        CassetteModel cassetteModel = (CassetteModel) getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.cassette_spinner_listed_item, parent, false);
        }

        TextView tv_cassetteTitle = (TextView) convertView.findViewById(R.id.cassette_title);
        tv_cassetteTitle.setText(cassetteModel.getTitle());

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
        return cassetteModelList.get(position).getId();
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
            convertView = inflater.inflate(R.layout.cassette_spinner_listed_item, parent, false);
        }

        TextView tv_cassetteTitle = (TextView) convertView.findViewById(R.id.cassette_title);
        tv_cassetteTitle.setText("WHERE DO I GO? " + cassetteModel.getTitle());

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
}
