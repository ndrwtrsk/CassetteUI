package nd.rw.cassetteui.app.view.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import nd.rw.cassetteui.app.model.CassetteModel;

public class CassettesListViewAdapter extends BaseAdapter{

    private List<CassetteModel> cassetteModelList;

    public CassettesListViewAdapter(List<CassetteModel> cassetteModelList) {
        this.cassetteModelList = cassetteModelList;
    }

    public void setCassetteModelList(List<CassetteModel> cassetteModelList) {
        this.cassetteModelList = cassetteModelList;
    }

    //region BaseAdapter methods

    @Override
    public int getCount() {
        return this.cassetteModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.cassetteModelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
//            view = View.inflate(viewGroup.getContext(), android.support.design.R.layout.)
        }

        return null;
    }

    //endregion BaseAdapter methods

//    private void fill
}
