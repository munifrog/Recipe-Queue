package com.example.recipe_q.adapt;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_q.R;
import com.example.recipe_q.model.Direction;

import java.util.List;

public class AdapterLinearDirections extends RecyclerView.Adapter<AdapterLinearDirections.DirectionHolder> {
    private List<Direction> mDirections;

    AdapterLinearDirections(
            @NonNull List<Direction> directions
    ) {
        mDirections = directions;
    }

    @NonNull
    @Override
    public DirectionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_direction, parent, false);
        return new DirectionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DirectionHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mDirections.size();
    }

    class DirectionHolder extends RecyclerView.ViewHolder {
        private TextView tvInstruction;
        private String mTextInstruction;
        private View mFocusScrim;
        private int mColorFocused;
        private int mColorNeutral;

        DirectionHolder(@NonNull View itemView) {
            super(itemView);
            Resources res = itemView.getResources();
            mColorFocused = res.getColor(R.color.scrim_focused);
            mColorNeutral = res.getColor(R.color.transparent);

            tvInstruction = itemView.findViewById(R.id.tv_direction_instruction);
            mTextInstruction = itemView.getResources().getString(R.string.recipe_directions_instruction);
            mFocusScrim = itemView.findViewById(R.id.iv_focus_scrim);

            itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    applyScrim(v);
                }
            });
        }

        private void applyScrim(View v) {
            mFocusScrim.setBackgroundColor(v.hasFocus() ? mColorFocused : mColorNeutral);
        }

        void bind(int position) {
            Direction bound = mDirections.get(position);
            tvInstruction.setText(String.format(mTextInstruction, (position + 1), bound.getInstructionOriginal()));
        }
    }
}
