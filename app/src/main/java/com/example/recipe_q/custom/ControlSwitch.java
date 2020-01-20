package com.example.recipe_q.custom;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.recipe_q.R;

public class ControlSwitch extends LinearLayout {
    private TextView mTvSummary;
    private Switch mSwitch;
    private String mTextCheckOff;
    private String mTextCheckOn;
    private View mSelectScrim;
    private int mColorFocused;
    private int mColorNeutral;
    private boolean mChecked;

    public ControlSwitch(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public ControlSwitch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public ControlSwitch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ControlSwitch);
        String title = a.getString(R.styleable.ControlSwitch_criteriaTitle);
        mChecked = a.getBoolean(R.styleable.ControlSwitch_criteriaChecked, false);
        mTextCheckOff = a.getString(R.styleable.ControlSwitch_summaryCheckOff);
        mTextCheckOn = a.getString(R.styleable.ControlSwitch_summaryCheckOn);
        a.recycle();

        View view = inflate(context,R.layout.item_summarized_switch, this);
        mSwitch = view.findViewById(R.id.switch_title);
        mSwitch.setText(title);
        mSwitch.setChecked(mChecked);
        mSwitch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                whenClicked();
            }
        });

        mTvSummary = view.findViewById(R.id.tv_summary);

        Resources res = getResources();
        mColorFocused = res.getColor(R.color.custom_focused);
        mColorNeutral = res.getColor(R.color.custom_neutral);

        mSelectScrim = view.findViewById(R.id.iv_custom_scrim);

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                whenClicked();
            }
        });

        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                applyScrim(hasFocus);
            }
        });

        updateSummary();
    }

    private void toggleChecked() {
        mChecked = !mChecked;
        mSwitch.setChecked(mChecked);
    }

    private void whenClicked() {
        toggleChecked();
        onCommit();
    }

    private void applyScrim(boolean hasFocus) {
        mSelectScrim.setBackgroundColor(hasFocus ? mColorFocused : mColorNeutral);
    }

    public boolean getChecked() {
        return mChecked;
    }

    private void updateSummary() {
        mTvSummary.setText(getChecked() ? mTextCheckOn : mTextCheckOff);
    }

    private void onCommit() {
        updateSummary();
    }
}
