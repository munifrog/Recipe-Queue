package com.example.recipe_q.custom;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.recipe_q.R;

public class ControlMinMax extends LinearLayout implements DialogMinMax.Listener {
    private DialogMinMax mDialog;

    private String mUnit;
    private TextView mSummary;

    private String mSummaryComplete;
    private String mSummaryAtLeast;
    private String mSummaryAtMost;
    private String mSummaryExactly;
    private String mSummaryBetween;
    private String mSummaryAny;
    private View mSelectScrim;
    private int mColorFocused;
    private int mColorNeutral;

    public ControlMinMax(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public ControlMinMax(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public ControlMinMax(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ControlMinMax);
        String title = a.getString(R.styleable.ControlMinMax_criteriaTitle);
        mUnit = a.getString(R.styleable.ControlMinMax_criteriaUnit);
        int titleRes = a.getResourceId(R.styleable.ControlMinMax_criteriaTitle, R.string.empty);
        int unitRes = a.getResourceId(R.styleable.ControlMinMax_criteriaUnit, R.string.empty);
        int minLabelRes = a.getResourceId(R.styleable.ControlMinMax_labelMinimum, R.string.empty);
        int maxLabelRes = a.getResourceId(R.styleable.ControlMinMax_labelMaximum, R.string.empty);
        int minValueRes = a.getResourceId(R.styleable.ControlMinMax_valueMinimum, R.string.empty);
        int maxValueRes = a.getResourceId(R.styleable.ControlMinMax_valueMaximum, R.string.empty);
        a.recycle();

        mDialog = new DialogMinMax(
                context,
                this,
                titleRes,
                unitRes,
                minLabelRes,
                maxLabelRes,
                minValueRes,
                maxValueRes
        );

        Resources res = context.getResources();
        mSummaryComplete = res.getString(R.string.min_max_summary_restrict_completely);
        mSummaryAtLeast = res.getString(R.string.min_max_summary_restrict_min);
        mSummaryAtMost = res.getString(R.string.min_max_summary_restrict_max);
        mSummaryExactly = res.getString(R.string.min_max_summary_restrict_same);
        mSummaryBetween = res.getString(R.string.min_max_summary_restrict_both);
        mSummaryAny = res.getString(R.string.min_max_summary_no_restriction);
        mColorFocused = res.getColor(R.color.custom_focused);
        mColorNeutral = res.getColor(R.color.custom_neutral);

        View view = inflate(context, R.layout.item_min_max, this);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        mSummary = view.findViewById(R.id.tv_item_summary);

        mSelectScrim = view.findViewById(R.id.iv_custom_scrim);

        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                applyScrim(hasFocus);
            }
        });

        updateSummary();
    }

    private void applyScrim(boolean hasFocus) {
        mSelectScrim.setBackgroundColor(hasFocus ? mColorFocused : mColorNeutral);
    }

    private void updateSummary() {
        String minString = mDialog.getMinimum();
        String maxString = mDialog.getMaximum();

        int minAmount = minString.isEmpty() ? 0 : Integer.parseInt(minString);
        if (maxString.isEmpty()) {
            // No maximum
            if (minAmount > 0) {
                mSummary.setText(String.format(mSummaryAtLeast, minAmount, mUnit));
            } else {
                mSummary.setText(mSummaryAny);
            }
        } else {
            int maxAmount = Integer.parseInt(maxString);
            if (maxAmount == 0) {
                mSummary.setText(mSummaryComplete);
            } else if (maxAmount == minAmount) {
                mSummary.setText(String.format(mSummaryExactly, minAmount, mUnit));
            } else if (minAmount == 0) {
                mSummary.setText(String.format(mSummaryAtMost, maxAmount, mUnit));
            } else {
                mSummary.setText(String.format(mSummaryBetween, minAmount, maxAmount, mUnit));
            }
        }
    }

    public DialogMinMax getDialog() {
        return mDialog;
    }

    @Override
    public void onCommit() {
        updateSummary();
    }

    @Override
    public void onCancel() {
        // Do nothing
    }
}
