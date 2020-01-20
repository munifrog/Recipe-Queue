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

import static com.example.recipe_q.adapt.AdapterLinear3Way.SELECTED_NEGATIVE;
import static com.example.recipe_q.adapt.AdapterLinear3Way.SELECTED_POSITIVE;

// https://stackoverflow.com/a/2695649
public class Control3WaySelect extends LinearLayout implements Dialog3WaySelect.Listener {
    private Dialog3WaySelect mDialog;

    private TextView mTvIncluded;
    private TextView mTvExcluded;
    private String mExcludedDefault;
    private String mIncludedDefault;
    private String mSummaryExclude;
    private String mSummaryInclude;
    private View mSelectScrim;
    private int mColorFocused;
    private int mColorNeutral;

    public Control3WaySelect(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public Control3WaySelect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public Control3WaySelect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Control3WaySelect);
        String title = a.getString(R.styleable.Control3WaySelect_criteriaTitle);
        int titleRef = a.getResourceId(R.styleable.Control3WaySelect_criteriaTitle, R.string.empty);
        int optionsRef = a.getResourceId(R.styleable.Control3WaySelect_criteriaItems, R.array.empty);
        mIncludedDefault = a.getString(R.styleable.Control3WaySelect_criteriaIncluded);
        mExcludedDefault = a.getString(R.styleable.Control3WaySelect_criteriaExcluded);
        a.recycle();

        mDialog = new Dialog3WaySelect(context, titleRef, optionsRef, this);
        View view = inflate(context,R.layout.item_search_3_way, this);

        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        mTvExcluded = view.findViewById(R.id.tv_summary_exclude);
        mTvIncluded = view.findViewById(R.id.tv_summary_include);

        mSummaryExclude = context.getString(R.string.summary_exclude);
        mSummaryInclude = context.getString(R.string.summary_include);

        Resources res = getResources();
        mColorFocused = res.getColor(R.color.custom_focused);
        mColorNeutral = res.getColor(R.color.custom_neutral);

        mSelectScrim = view.findViewById(R.id.iv_custom_scrim);

        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                applyScrim(hasFocus);
            }
        });

        updateSummaries();
    }

    private void applyScrim(boolean hasFocus) {
        mSelectScrim.setBackgroundColor(hasFocus ? mColorFocused : mColorNeutral);
    }

    public Dialog3WaySelect getDialog() {
        return mDialog;
    }

    public void updateSummaries() {
        String included = mDialog.getSelectionList(SELECTED_POSITIVE, mIncludedDefault);
        mTvIncluded.setText(String.format(mSummaryInclude, included));

        String excluded = mDialog.getSelectionList(SELECTED_NEGATIVE, mExcludedDefault);
        mTvExcluded.setVisibility( excluded.equals(included) ? GONE : VISIBLE );
        mTvExcluded.setText(String.format(mSummaryExclude, excluded));
    }

    @Override
    public void onCommit() {
        updateSummaries();
    }

    @Override
    public void onCancel() {
        // Do nothing
    }
}
