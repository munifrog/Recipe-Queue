package com.example.recipe_q.adapt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_q.R;

public class AdapterLinear3Way extends RecyclerView.Adapter<AdapterLinear3Way.ListItemHolder> {
    public static final int SELECTED_NEUTRAL = 0;
    public static final int SELECTED_NEGATIVE = 1;
    public static final int SELECTED_POSITIVE = 2;

    private String [] mNames; // Full list of options
    private int [] mSelections; // Positive/include, neutral/ignore, or negative/exclude

    public AdapterLinear3Way(@NonNull String [] options, int [] selections) {
        mNames = options;
        mSelections = selections;
    }

    public int [] getSelections() {
        return mSelections;
    }
    public void setSelections(int [] selections) {
        mSelections = selections;
    }

    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_dialog_3_way, parent, false);
        return new ListItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNames.length;
    }

    class ListItemHolder extends RecyclerView.ViewHolder {
        private RadioButton mPositive;
        private RadioButton mNeutral;
        private RadioButton mNegative;
        private TextView mName;

        ListItemHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.tv_option_name);
            mNegative = itemView.findViewById(R.id.radio_negative);
            mNeutral = itemView.findViewById(R.id.radio_neutral);
            mPositive = itemView.findViewById(R.id.radio_positive);

            mNegative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelections[getAdapterPosition()] = SELECTED_NEGATIVE;
                }
            });
            mNeutral.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelections[getAdapterPosition()] = SELECTED_NEUTRAL;
                }
            });
            mPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelections[getAdapterPosition()] = SELECTED_POSITIVE;
                }
            });
        }

        void bind(int position) {
            mName.setText(mNames[position]);

            switch (mSelections[position]) {
                default:
                case SELECTED_NEUTRAL:
                    mNegative.setChecked(false);
                    mNeutral.setChecked(true);
                    mPositive.setChecked(false);
                    break;
                case SELECTED_NEGATIVE:
                    mNegative.setChecked(true);
                    mNeutral.setChecked(false);
                    mPositive.setChecked(false);
                    break;
                case SELECTED_POSITIVE:
                    mNegative.setChecked(false);
                    mNeutral.setChecked(false);
                    mPositive.setChecked(true);
                    break;
            }
        }
    }
}
