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
import com.example.recipe_q.custom.ControlMultiSelect;
import com.example.recipe_q.custom.ControlSingleSelect;
import com.example.recipe_q.custom.ControlSwitch;
import com.example.recipe_q.custom.Dialog3WaySelect;
import com.example.recipe_q.custom.DialogMultiSelect;
import com.example.recipe_q.custom.DialogSingleSelect;

public class SearchCommonFragment extends Fragment {
    private Control3WaySelect mCuisine;
    private ControlSingleSelect mDiet;
    private ControlMultiSelect mIntolerance;
    private Control3WaySelect mIngredients;
    private ControlSingleSelect mMealType;
    private ControlSwitch mRequireIngredients;

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
        mDiet = rootView.findViewById(R.id.custom_diet);
        mDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDietClick();
            }
        });
        mIntolerance = rootView.findViewById(R.id.custom_intolerance);
        mIntolerance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIntoleranceClick();
            }
        });
        mIngredients = rootView.findViewById(R.id.custom_ingredients);
        mIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIngredientClick();
            }
        });
        mMealType = rootView.findViewById(R.id.custom_meal_type);
        mMealType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMealTypeClick();
            }
        });
        mRequireIngredients = rootView.findViewById(R.id.switch_require_ingredients);
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
            if (fragmentManager != null) {
                dialog.show(fragmentManager, SearchActivity.class.getSimpleName());
            }
        }
    }

    private void onDietClick() {
        DialogSingleSelect dialog = mDiet.getDialog();
        if (dialog != null) {
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null) {
                dialog.show(fragmentManager, SearchActivity.class.getSimpleName());
            }
        }
    }

    private void onIntoleranceClick() {
        DialogMultiSelect dialog = mIntolerance.getDialog();
        if (dialog != null) {
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null) {
                dialog.show(fragmentManager, SearchActivity.class.getSimpleName());
            }
        }
    }

    private void onIngredientClick() {
        Dialog3WaySelect dialog = mIngredients.getDialog();
        if (dialog != null) {
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null) {
                dialog.show(fragmentManager, SearchActivity.class.getSimpleName());
            }
        }
    }

    private void onMealTypeClick() {
        DialogSingleSelect dialog = mMealType.getDialog();
        if (dialog != null) {
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null) {
                dialog.show(fragmentManager, SearchActivity.class.getSimpleName());
            }
        }
    }
}
