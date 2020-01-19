package com.example.recipe_q.adapt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_q.R;
import com.example.recipe_q.model.DirectionGroup;

import java.util.List;

public class AdapterLinearDirectionGroups extends RecyclerView.Adapter<AdapterLinearDirectionGroups.DirectionGroupHolder> {
    private List<DirectionGroup> mDirectionGroups;

    public AdapterLinearDirectionGroups(
            @NonNull List<DirectionGroup> directionGroups
    ) {
        mDirectionGroups = directionGroups;
    }

    public void setDirections(List<DirectionGroup> directionGroups) {
        mDirectionGroups = directionGroups;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DirectionGroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_direction_group, parent, false);
        return new DirectionGroupHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DirectionGroupHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mDirectionGroups.size();
    }

    class DirectionGroupHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private LinearLayout mHeader;
        private RecyclerView mRecyclerView;

        DirectionGroupHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.tv_direction_group_name);
            mHeader = itemView.findViewById(R.id.frame_direction_header);
            mRecyclerView = itemView.findViewById(R.id.rv_direction_group);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }

        void bind(int position) {
            DirectionGroup bound = mDirectionGroups.get(position);
            String name = bound.getGroupName();
            mName.setText(name);
            mHeader.setVisibility(name.isEmpty() ? View.GONE : View.VISIBLE);
            mRecyclerView.setAdapter(new AdapterLinearDirections(bound.getGroupSteps()));
        }
    }
}
