package com.example.recipe_q.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.recipe_q.R;
import com.example.recipe_q.activity.SearchActivity;
import com.example.recipe_q.custom.Control3WaySelect;
import com.example.recipe_q.custom.Dialog3WaySelect;

public class SearchCommonFragment extends Fragment {
    private Control3WaySelect mCuisine;
    private Control3WaySelect mIngredients;

    public SearchCommonFragment() {}

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View rootView = inflater.inflate(R.layout.fragment_search_common, container, false);
        mCuisine = rootView.findViewById(R.id.custom_cuisine);
        mCuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCuisineClick();
            }
        });
        mIngredients = rootView.findViewById(R.id.custom_ingredients);
        mIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIngredientClick();
            }
        });
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void onCuisineClick() {
        Dialog3WaySelect dialog = mCuisine.getDialog();
        if (dialog != null) {
            FragmentManager fragmentManager = getFragmentManager();
            dialog.show(fragmentManager, SearchActivity.class.getSimpleName());
        }
    }

    private void onIngredientClick() {
        Dialog3WaySelect dialog = mIngredients.getDialog();
        if (dialog != null) {
            FragmentManager fragmentManager = getFragmentManager();
            dialog.show(fragmentManager, SearchActivity.class.getSimpleName());
        }
    }
}
