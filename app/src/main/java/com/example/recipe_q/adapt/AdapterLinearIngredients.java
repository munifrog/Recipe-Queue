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
import com.example.recipe_q.model.Ingredient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterLinearIngredients extends RecyclerView.Adapter<AdapterLinearIngredients.IngredientHolder> {
    private List<Ingredient> mIngredients;
    private String mTextQuantity;

    public AdapterLinearIngredients(
            @NonNull List<Ingredient> ingredients
    ) {
        mIngredients = ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mTextQuantity = parent.getResources().getString(R.string.recipe_ingredient_amount);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_ingredient, parent, false);
        return new IngredientHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() { return mIngredients.size(); }

    class IngredientHolder extends RecyclerView.ViewHolder {
        private ImageView mIngredientImage;
        private TextView mName;
        private TextView mQuantity;
        private View mFocusScrim;
        private int mColorFocused;
        private int mColorNeutral;

        IngredientHolder(@NonNull View itemView) {
            super(itemView);
            Resources res = itemView.getResources();
            mColorFocused = res.getColor(R.color.scrim_focused);
            mColorNeutral = res.getColor(R.color.transparent);

            mIngredientImage = itemView.findViewById(R.id.iv_ingredient_image);
            mName = itemView.findViewById(R.id.iv_ingredient_name);
            mQuantity = itemView.findViewById(R.id.iv_ingredient_amount);
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
            Ingredient bound = mIngredients.get(position);
            Picasso.get().load(bound.getImage()).placeholder(R.drawable.preparation).into(mIngredientImage);
            mName.setText(bound.getIngredientName());
            mQuantity.setText(String.format(mTextQuantity, bound.getAmount(), bound.getUnit()));
        }
    }
}
