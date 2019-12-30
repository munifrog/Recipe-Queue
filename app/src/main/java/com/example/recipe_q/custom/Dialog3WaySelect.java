package com.example.recipe_q.custom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_q.R;
import com.example.recipe_q.adapt.AdapterLinear3Way;

import static com.example.recipe_q.adapt.AdapterLinear3Way.SELECTED_NEUTRAL;

public class Dialog3WaySelect extends DialogFragment {
    public static final String LIST_SEPARATOR = ", ";

    private static final String SAVED_ENTRIES = "entries";
    private static final String SAVED_TITLE = "title";
    private static final String SAVED_SELECTIONS = "selections";

    private String mTitle;
    private String [] mEntries;
    private int [] mSelections; // Positive/include, negative/exclude, or neutral/ignore
    private Listener mListener;

    public interface Listener {
        void onCommit();
        void onCancel();
    }

    Dialog3WaySelect(
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

    public Dialog3WaySelect() {

    }

    private void resetSelections() {
        mSelections = new int [mEntries.length];
        for (int i = 0; i < mEntries.length; i++) {
            mSelections[i] = SELECTED_NEUTRAL;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mTitle = savedInstanceState.getString(SAVED_TITLE);
            mEntries = savedInstanceState.getStringArray(SAVED_ENTRIES);
            mSelections = savedInstanceState.getIntArray(SAVED_SELECTIONS);
        }

        Activity activity = getActivity();
        LayoutInflater inflater = activity.getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.content_dialog_3_way, null);
        RecyclerView rvDialog = dialogView.findViewById(R.id.rv_dialog_list);
        rvDialog.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        final AdapterLinear3Way adapter = new AdapterLinear3Way(mEntries, mSelections.clone());
        rvDialog.setAdapter(adapter);

        Resources resources = activity.getResources();
        String titleText = resources.getString(R.string.dialog_title_3_way);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity())
                .setTitle(String.format(titleText, mTitle))
                .setView(dialogView)
                .setPositiveButton(R.string.dialog_btn_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Commit the selections
                        mSelections = adapter.getSelections();
                        mListener.onCommit();
                    }
                })
                .setNegativeButton(R.string.dialog_btn_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dialog cancelled; discard selections; probably do nothing
                        adapter.setSelections(mSelections.clone());
                        mListener.onCancel();
                    }
                })
        ;

        return dialogBuilder.create();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_TITLE, mTitle);
        outState.putStringArray(SAVED_ENTRIES, mEntries);
        outState.putIntArray(SAVED_SELECTIONS, mSelections);
    }

    public String getSelectionList(int selection, String noneList) {
        int count = 0;
        StringBuilder listBuilder = new StringBuilder();
        for (int i = 0; i < mSelections.length; i++) {
            if (mSelections[i] == selection) {
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
