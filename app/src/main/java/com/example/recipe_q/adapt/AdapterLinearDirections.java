package com.example.recipe_q.adapt;

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
        TextView tvInstruction;
        String mTextInstruction;

        DirectionHolder(@NonNull View itemView) {
            super(itemView);
            tvInstruction = itemView.findViewById(R.id.tv_direction_instruction);
            mTextInstruction = itemView.getResources().getString(R.string.recipe_directions_instruction);
        }

        void bind(int position) {
            Direction bound = mDirections.get(position);
            tvInstruction.setText(String.format(mTextInstruction, (position + 1), bound.getInstructionOriginal()));
        }
    }
}
