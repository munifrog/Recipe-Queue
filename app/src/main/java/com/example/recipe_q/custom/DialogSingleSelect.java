package com.example.recipe_q.custom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.recipe_q.R;

public class DialogSingleSelect extends DialogFragment {

    private String mTitle;
    private String [] mEntries;
    private int mSelectionAuthority;
    private int mSelectionTemporary;
    private Listener mListener;

    public interface Listener {
        void onCommit();
        void onCancel();
    }

    DialogSingleSelect(
            Context context,
            int titleResource,
            int entryArrayResource,
            int position,
            Listener listener
    ) {
        Resources resources = context.getResources();
        mTitle = resources.getString(titleResource);
        mEntries = resources.getStringArray(entryArrayResource);
        mListener = listener;
        mSelectionAuthority = position;
        mSelectionTemporary = mSelectionAuthority;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity())
                .setTitle(mTitle)
                .setPositiveButton(R.string.dialog_btn_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSelectionAuthority = mSelectionTemporary;
                        mListener.onCommit();
                    }
                })
                .setNegativeButton(R.string.dialog_btn_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSelectionTemporary = mSelectionAuthority;
                        mListener.onCancel();
                    }
                })
                .setSingleChoiceItems(mEntries, mSelectionAuthority, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSelectionTemporary = which;
                    }
                })
        ;

        return dialogBuilder.create();
    }

    public String getSelection() {
        return mEntries[mSelectionAuthority];
    }
}
