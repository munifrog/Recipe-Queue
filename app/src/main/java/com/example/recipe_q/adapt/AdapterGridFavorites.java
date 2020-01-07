package com.example.recipe_q.adapt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_q.R;
import com.example.recipe_q.model.FavoriteRecipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterGridFavorites extends RecyclerView.Adapter<AdapterGridFavorites.FavoriteHolder> {
    private List<FavoriteRecipe> mFavorites;
    private Listener mListener;

    public AdapterGridFavorites(@NonNull List<FavoriteRecipe> recipes, Listener listener) {
        mFavorites = recipes;
        mListener = listener;
    }

    public interface Listener {
        void onClick(FavoriteRecipe favorite);
    }

    public void setFavorites(List<FavoriteRecipe> recipes) {
        mFavorites = recipes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_grid_search_result, parent, false);
        return new FavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mFavorites == null ? 0 : mFavorites.size();
    }

    class FavoriteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mRecipeImage;
        TextView mRecipeName;

        FavoriteHolder(@NonNull View itemView) {
            super(itemView);
            mRecipeImage = itemView.findViewById(R.id.iv_recipe_image);
            mRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            itemView.setOnClickListener(this);
        }

        void bind(int position) {
            FavoriteRecipe favorite = mFavorites.get(position);
            Picasso.get().load(favorite.getImage()).placeholder(R.drawable.ic_launcher_background).into(mRecipeImage);
            mRecipeName.setText(favorite.getRecipeTitle());
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(mFavorites.get(getAdapterPosition()));
        }
    }
}
