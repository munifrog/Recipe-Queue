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
import com.example.recipe_q.custom.ControlSwitch;

import java.util.Map;

public class SearchIngredientFragment extends Fragment {
    private static final int INGREDIENTS_INTOLERANCE         =  0;
    private static final int INGREDIENTS_INCLUDE_EXCLUDE     =  1;
    private static final int INGREDIENTS_IGNORE_PANTRY       =  2;

    private ControlMultiSelect mIntolerance;
    private Control3WaySelect mIngredients;
    private ControlSwitch mIgnorePantry;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View rootView = inflater.inflate(R.layout.fragment_search_ingredients, container, false);
        mIntolerance = rootView.findViewById(R.id.custom_intolerance);
        mIntolerance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(INGREDIENTS_INTOLERANCE);
            }
        });
        mIngredients = rootView.findViewById(R.id.custom_ingredients);
        mIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(INGREDIENTS_INCLUDE_EXCLUDE);
            }
        });
        mIgnorePantry = rootView.findViewById(R.id.switch_ignore_pantry);
        return rootView;
    }

    private DialogFragment getDialog(int type) {
        DialogFragment dialog;
        switch (type) {
            case INGREDIENTS_INTOLERANCE:
                dialog = mIntolerance.getDialog();
                break;
            case INGREDIENTS_INCLUDE_EXCLUDE:
                dialog = mIngredients.getDialog();
                break;
            default:
            case INGREDIENTS_IGNORE_PANTRY:
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

    public void addSearchTerms(@NonNull Map<String, String> searchTerms) {

    }
}
