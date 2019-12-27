package com.example.recipe_q.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.recipe_q.R;
import com.example.recipe_q.activity.SearchActivity;
import com.example.recipe_q.custom.Control3WaySelect;
import com.example.recipe_q.custom.ControlMultiSelect;
import com.example.recipe_q.custom.ControlSingleSelect;
import com.example.recipe_q.custom.ControlSwitch;

public class SearchCommonFragment extends Fragment {
    private static final int COMMON_CUISINE                 =  0;
    private static final int COMMON_DIET                    =  1;
    private static final int COMMON_INTOLERANCE             =  2;
    private static final int COMMON_INGREDIENTS             =  3;
    private static final int COMMON_MEAL_TYPE               =  4;
    private static final int COMMON_REQUIRE_INGREDIENTS     =  5;

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
                onControlClick(COMMON_CUISINE);
            }
        });
        mDiet = rootView.findViewById(R.id.custom_diet);
        mDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(COMMON_DIET);
            }
        });
        mIntolerance = rootView.findViewById(R.id.custom_intolerance);
        mIntolerance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(COMMON_INTOLERANCE);
            }
        });
        mIngredients = rootView.findViewById(R.id.custom_ingredients);
        mIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(COMMON_INGREDIENTS);
            }
        });
        mMealType = rootView.findViewById(R.id.custom_meal_type);
        mMealType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(COMMON_MEAL_TYPE);
            }
        });
        mRequireIngredients = rootView.findViewById(R.id.switch_require_ingredients);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private DialogFragment getDialog(int type) {
        DialogFragment dialog;
        switch (type) {
            case COMMON_CUISINE:
                dialog = mCuisine.getDialog();
                break;
            case COMMON_DIET:
                dialog = mDiet.getDialog();
                break;
            case COMMON_INTOLERANCE:
                dialog = mIntolerance.getDialog();
                break;
            case COMMON_INGREDIENTS:
                dialog = mIngredients.getDialog();
                break;
            case COMMON_MEAL_TYPE:
                dialog = mMealType.getDialog();
                break;
            default:
            case COMMON_REQUIRE_INGREDIENTS:
                dialog = null;
                break;
        }
        return dialog;
    }

    private void onControlClick(int type) {
        DialogFragment dialog = getDialog(type);
        if (dialog != null) {
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null) {
                dialog.show(fragmentManager, SearchActivity.class.getSimpleName());
            }
        }
    }
}
