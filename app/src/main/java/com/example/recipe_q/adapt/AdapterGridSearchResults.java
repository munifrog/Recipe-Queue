package com.example.recipe_q.adapt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_q.R;
import com.example.recipe_q.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterGridSearchResults extends RecyclerView.Adapter<AdapterGridSearchResults.ListItemHolder> {
    private List<Recipe> mRecipes;
    private Listener mListener;

    public AdapterGridSearchResults(@NonNull List<Recipe> recipes, Listener listener) {
        mRecipes = recipes;
        mListener = listener;
    }

    public interface Listener {
        void onClick(Recipe recipe);
    }

    public void setRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_grid_search_result, parent, false);
        return new ListItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mRecipes == null ? 0 : mRecipes.size();
    }

    class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mRecipeImage;
        TextView mRecipeName;

        ListItemHolder(@NonNull View itemView) {
            super(itemView);
            mRecipeImage = itemView.findViewById(R.id.iv_recipe_image);
            mRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            itemView.setOnClickListener(this);
        }

        void bind(int position) {
            Recipe recipe = mRecipes.get(position);
            Picasso.get().load(recipe.getImage()).placeholder(R.drawable.ic_launcher_background).into(mRecipeImage);
            mRecipeName.setText(recipe.getRecipeTitle());
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(mRecipes.get(getAdapterPosition()));
        }
    }
}
