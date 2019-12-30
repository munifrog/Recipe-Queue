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
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.recipe_q.R;

public class DialogMinMax extends DialogFragment {
    private Listener mListener;

    private String mTitle;
    private String mUnit;
    private String mLabelMinimum;
    private String mLabelMaximum;

    private EditText mEtMinimum;
    private EditText mEtMaximum;

    private String mValueMinimum;
    private String mValueMaximum;

    public interface Listener {
        void onCommit();
        void onCancel();
    }

    DialogMinMax(
            Context context,
            Listener listener,
            int titleResource,
            int unitResource,
            int minLabelResource,
            int maxLabelResource,
            int minValueResource,
            int maxValueResource
    ) {
        mListener = listener;
        Resources res = context.getResources();
        mTitle = res.getString(titleResource);
        mUnit = res.getString(unitResource);
        mLabelMinimum = res.getString(minLabelResource);
        mLabelMaximum = res.getString(maxLabelResource);
        mValueMinimum = res.getString(minValueResource);
        mValueMaximum = res.getString(maxValueResource);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Activity activity = getActivity();
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.item_dialog_min_max, null);
        TextView tvMinLabel = dialogView.findViewById(R.id.tv_minimum_label);
        tvMinLabel.setText(mLabelMinimum);
        TextView tvMaxLabel = dialogView.findViewById(R.id.tv_maximum_label);
        tvMaxLabel.setText(mLabelMaximum);
        TextView tvMinUnit = dialogView.findViewById(R.id.tv_minimum_unit);
        tvMinUnit.setText(mUnit);
        TextView tvMaxUnit = dialogView.findViewById(R.id.tv_maximum_unit);
        tvMaxUnit.setText(mUnit);
        mEtMinimum = dialogView.findViewById(R.id.et_minimum_amount);
        mEtMinimum.setText(mValueMinimum);
        mEtMaximum = dialogView.findViewById(R.id.et_maximum_amount);
        mEtMaximum.setText(mValueMaximum);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity())
                .setTitle(mTitle)
                .setView(dialogView)
                .setPositiveButton(R.string.dialog_btn_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        determineValues();
                        mListener.onCommit();
                    }
                })
                .setNegativeButton(R.string.dialog_btn_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onCancel();
                    }
                })
        ;

        return dialogBuilder.create();
    }

    private void determineValues() {
        String strMinimum = mEtMinimum.getText().toString();
        int integerMin = strMinimum.isEmpty() ? 0 : Math.abs(Integer.parseInt(strMinimum));
        String strMaximum = mEtMaximum.getText().toString();
        if (strMaximum.isEmpty()) {
            mValueMaximum = "";
        } else {
            int integerMax = Math.abs(Integer.parseInt(strMaximum));
            if (integerMin > integerMax) {
                int temp = integerMin;
                integerMin = integerMax;
                integerMax = temp;
            }
            mValueMaximum = Integer.toString(integerMax);
        }
        mValueMinimum = Integer.toString(integerMin);
    }

    public String getMinimum() {
        return mValueMinimum;
    }

    public String getMaximum() {
        return mValueMaximum;
    }
}
