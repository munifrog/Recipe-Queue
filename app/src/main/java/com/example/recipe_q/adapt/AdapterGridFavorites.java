package com.example.recipe_q.adapt;

import android.content.res.Resources;
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
        private ImageView mRecipeImage;
        private TextView mRecipeName;
        private View mRecipeScrim;
        private int mColorFocused;
        private int mColorPressed;
        private int mColorNeutral;

        FavoriteHolder(@NonNull View itemView) {
            super(itemView);
            Resources res = itemView.getResources();
            mColorFocused = res.getColor(R.color.scrim_focused);
            mColorPressed = res.getColor(R.color.scrim_pressed);
            mColorNeutral = res.getColor(R.color.transparent);

            mRecipeImage = itemView.findViewById(R.id.iv_recipe_image);
            mRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            mRecipeScrim = itemView.findViewById(R.id.iv_recipe_scrim);

            itemView.setOnClickListener(this);
            itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    applyScrim(v, false);
                }
            });
        }

        private void applyScrim(View v, boolean clicked) {
            int color;
            if (v.hasFocus()) {
                if (clicked) {
                    color = mColorPressed;
                } else {
                    color = mColorFocused;
                }
            } else {
                color = mColorNeutral;
            }
            mRecipeScrim.setBackgroundColor(color);
        }

        void bind(int position) {
            FavoriteRecipe favorite = mFavorites.get(position);
            Picasso.get().load(favorite.getImage()).placeholder(R.drawable.preparation).into(mRecipeImage);
            mRecipeName.setText(favorite.getRecipeTitle());
        }

        @Override
        public void onClick(View v) {
            applyScrim(v,true);
            mListener.onClick(mFavorites.get(getAdapterPosition()));
        }
    }
}
