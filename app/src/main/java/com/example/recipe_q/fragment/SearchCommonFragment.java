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
import com.example.recipe_q.custom.ControlSingleSelect;
import com.example.recipe_q.custom.ControlSwitch;
import com.example.recipe_q.custom.Dialog3WaySelect;
import com.example.recipe_q.custom.DialogSingleSelect;

import java.util.Map;

import static com.example.recipe_q.adapt.AdapterLinear3Way.SELECTED_NEGATIVE;
import static com.example.recipe_q.adapt.AdapterLinear3Way.SELECTED_POSITIVE;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_CUISINE_EXCLUDE;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_CUISINE_INCLUDE;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_DIET;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEAL_TYPE;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_REQUIRE_INSTRUCTIONS;

public class SearchCommonFragment extends Fragment {
    private static final String EMPTY_STRING                = "";
    private static final String LIST_SEPARATOR_REPLACED     = ",";

    private static final int COMMON_CUISINE                 =  0;
    private static final int COMMON_DIET                    =  1;
    private static final int COMMON_MEAL_TYPE               =  2;
    private static final int COMMON_REQUIRE_INSTRUCTIONS    =  3;

    private Control3WaySelect mCuisine;
    private ControlSingleSelect mDiet;
    private ControlSingleSelect mMealType;
    private ControlSwitch mRequireInstructions;

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
        mMealType = rootView.findViewById(R.id.custom_meal_type);
        mMealType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(COMMON_MEAL_TYPE);
            }
        });
        mRequireInstructions = rootView.findViewById(R.id.switch_require_instructions);
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
            case COMMON_MEAL_TYPE:
                dialog = mMealType.getDialog();
                break;
            default:
            case COMMON_REQUIRE_INSTRUCTIONS:
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
        String selection;
        Dialog3WaySelect threeWayDialog = mCuisine.getDialog();
        selection = threeWayDialog.getSelectionList(SELECTED_NEGATIVE, EMPTY_STRING);
        if (!selection.isEmpty()) {
            selection = selection.replaceAll(Dialog3WaySelect.LIST_SEPARATOR, LIST_SEPARATOR_REPLACED);
            searchTerms.put(QUERY_COMPLEX_CUISINE_EXCLUDE, selection);
        }
        selection = threeWayDialog.getSelectionList(SELECTED_POSITIVE, EMPTY_STRING);
        if (!selection.isEmpty()) {
            selection = selection.replaceAll(Dialog3WaySelect.LIST_SEPARATOR, LIST_SEPARATOR_REPLACED);
            searchTerms.put(QUERY_COMPLEX_CUISINE_INCLUDE, selection);
        }

        DialogSingleSelect singleDialog = mDiet.getDialog();
        selection = singleDialog.getSelection();
        if (!selection.isEmpty()) {
            searchTerms.put(QUERY_COMPLEX_DIET, selection);
        }

        singleDialog = mMealType.getDialog();
        selection = singleDialog.getSelection();
        if (!selection.isEmpty()) {
            searchTerms.put(QUERY_COMPLEX_MEAL_TYPE, selection);
        }

        if (mRequireInstructions.getChecked()) {
            searchTerms.put(QUERY_COMPLEX_REQUIRE_INSTRUCTIONS, Boolean.toString(true));
        }
    }
}
