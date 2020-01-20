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

public class ControlMultiSelect extends LinearLayout implements DialogMultiSelect.Listener {
    private DialogMultiSelect mDialog;
    private TextView mTvSummary;
    private String mTextSummary;
    private String mTextEmptyList;
    private View mSelectScrim;
    private int mColorFocused;
    private int mColorNeutral;

    public ControlMultiSelect(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public ControlMultiSelect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public ControlMultiSelect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ControlMultiSelect);
        String title = a.getString(R.styleable.ControlMultiSelect_criteriaTitle);
        int titleRef = a.getResourceId(R.styleable.ControlMultiSelect_criteriaTitle, R.string.empty);
        int optionsRef = a.getResourceId(R.styleable.ControlMultiSelect_criteriaItems, R.array.empty);
        int summaryRef = a.getResourceId(R.styleable.ControlMultiSelect_criteriaSummary, R.string.empty);
        a.recycle();

        Resources res = context.getResources();
        mTextSummary = res.getString(summaryRef);
        mTextEmptyList = res.getString(R.string.search_common_selection_none);
        mColorFocused = res.getColor(R.color.custom_focused);
        mColorNeutral = res.getColor(R.color.custom_neutral);

        mDialog = new DialogMultiSelect(context, titleRef, optionsRef, this);

        View view = inflate(context,R.layout.item_search_single, this);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        mTvSummary = view.findViewById(R.id.tv_summary);

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

    public DialogMultiSelect getDialog() {
        return mDialog;
    }

    private void updateSummary() {
        mTvSummary.setText(String.format(mTextSummary, mDialog.getSelectionList(mTextEmptyList)));
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
