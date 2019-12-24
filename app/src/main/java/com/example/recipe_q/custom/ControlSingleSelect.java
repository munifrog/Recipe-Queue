package com.example.recipe_q.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.recipe_q.R;

public class ControlSingleSelect extends LinearLayout implements DialogSingleSelect.Listener {
    private DialogSingleSelect mDialog;
    private TextView mTvSummary;

    public ControlSingleSelect(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public ControlSingleSelect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public ControlSingleSelect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ControlSingleSelect);
        String title = a.getString(R.styleable.ControlSingleSelect_criteriaTitle);
        int titleRef = a.getResourceId(R.styleable.ControlSingleSelect_criteriaTitle, R.string.empty);
        int optionsRef = a.getResourceId(R.styleable.ControlSingleSelect_criteriaItems, R.array.empty);
        int selection = a.getInt(R.styleable.ControlSingleSelect_criteriaSelection, 0);
        a.recycle();

        mDialog = new DialogSingleSelect(context, titleRef, optionsRef, selection, this);

        View view = inflate(context,R.layout.item_search_single, this);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        mTvSummary = view.findViewById(R.id.tv_summary);
        updateSummary();
    }

    public DialogSingleSelect getDialog() {
        return mDialog;
    }

    private void updateSummary() {
        mTvSummary.setText(mDialog.getSelection());
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
