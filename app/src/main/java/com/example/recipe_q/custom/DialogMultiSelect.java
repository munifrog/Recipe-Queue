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

public class DialogMultiSelect extends DialogFragment {
    public static final String LIST_SEPARATOR = ", ";

    private String mTitle;
    private String [] mEntries;
    private boolean [] mSelectionAuthority;
    private boolean [] mSelectionTemporary;
    private Listener mListener;

    public interface Listener {
        void onCommit();
        void onCancel();
    }

    DialogMultiSelect(
            Context context,
            int titleResource,
            int entryArrayResource,
            Listener listener
    ) {
        Resources resources = context.getResources();
        mTitle = resources.getString(titleResource);
        mEntries = resources.getStringArray(entryArrayResource);
        mListener = listener;
        resetSelections();
    }

    private void resetSelections() {
        mSelectionAuthority = new boolean [mEntries.length];
        for (int i = 0; i < mEntries.length; i++) {
            mSelectionAuthority[i] = false;
        }
        mSelectionTemporary = mSelectionAuthority.clone();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity())
                .setTitle(mTitle)
                .setPositiveButton(R.string.dialog_btn_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSelectionAuthority = mSelectionTemporary.clone();
                        mListener.onCommit();
                    }
                })
                .setNegativeButton(R.string.dialog_btn_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSelectionTemporary = mSelectionAuthority.clone();
                        mListener.onCancel();
                    }
                })
                .setMultiChoiceItems(mEntries, mSelectionTemporary, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        mSelectionTemporary[which] = isChecked;
                    }
                })
        ;

        return dialogBuilder.create();
    }

    public String getSelectionList(String noneList) {
        int count = 0;
        StringBuilder listBuilder = new StringBuilder();
        for (int i = 0; i < mSelectionAuthority.length; i++) {
            if (mSelectionAuthority[i]) {
                if (count > 0) {
                    listBuilder.append(LIST_SEPARATOR);
                }
                listBuilder.append(mEntries[i]);
                count++;
            }
        }

        return (count > 0 ? listBuilder.toString() : noneList);
    }
}
