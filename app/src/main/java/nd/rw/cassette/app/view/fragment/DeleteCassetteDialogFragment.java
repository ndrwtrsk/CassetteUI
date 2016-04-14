package nd.rw.cassette.app.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import nd.rw.cassette.R;

public class DeleteCassetteDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity(), R.style.AppCompatAlertDialogStyle);

        builder.setMessage(R.string.delete_cassette_dialog_title)
                .setPositiveButton(R.string.delete_cassette_dialog_confirm, positiveListener)
                .setNegativeButton(R.string.delete_cassette_dialog_no, negativeListener);

        return builder.create();
    }


    //region Events and Listeners

    private DeleteCassetteNoticeListener listener;

    public interface DeleteCassetteNoticeListener {
        void onDialogPositiveClick(DialogFragment dialog);
    }

    private DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            listener.onDialogPositiveClick(DeleteCassetteDialogFragment.this);
        }
    };

    private DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            //  do nothing
        }
    };

    //endregion Events and Listeners

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (DeleteCassetteNoticeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

}