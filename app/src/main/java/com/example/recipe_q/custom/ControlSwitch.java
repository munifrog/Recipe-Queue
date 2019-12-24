package com.example.recipe_q.custom;

import android.content.Context;
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
        boolean checked = a.getBoolean(R.styleable.ControlSwitch_criteriaChecked, false);
        mTextCheckOff = a.getString(R.styleable.ControlSwitch_summaryCheckOff);
        mTextCheckOn = a.getString(R.styleable.ControlSwitch_summaryCheckOn);
        a.recycle();

        View view = inflate(context,R.layout.item_summarized_switch, this);
        mSwitch = view.findViewById(R.id.switch_title);
        mSwitch.setText(title);
        mSwitch.setChecked(checked);
        mSwitch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onCommit();
            }
        });
        mTvSummary = view.findViewById(R.id.tv_summary);
        updateSummary();
    }

    private void updateSummary() {
        mTvSummary.setText(mSwitch.isChecked() ? mTextCheckOn : mTextCheckOff);
    }

    private void onCommit() {
        updateSummary();
    }
}
