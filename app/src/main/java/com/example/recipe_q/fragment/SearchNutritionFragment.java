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
import com.example.recipe_q.custom.ControlMinMax;
import com.example.recipe_q.custom.DialogMinMax;

public class SearchNutritionFragment extends Fragment {
    private ControlMinMax mAlcohol;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View rootView = inflater.inflate(R.layout.fragment_search_nutrition, container, false);
        mAlcohol = rootView.findViewById(R.id.mm_alcohol);
        mAlcohol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAlcoholClick();
            }
        });
        return rootView;
    }

    private void onAlcoholClick() {
        DialogMinMax dialog = mAlcohol.getDialog();
        if (dialog != null) {
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null) {
                dialog.show(fragmentManager, SearchActivity.class.getSimpleName());
            }
        }
    }
}
