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
import com.example.recipe_q.custom.ControlMinMax;
import com.example.recipe_q.custom.DialogMinMax;

import java.util.HashMap;
import java.util.Map;

import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_ALCOHOL_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_ALCOHOL_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_CAFFEINE_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_CAFFEINE_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_CALCIUM_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_CALCIUM_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_CALORY_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_CALORY_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_CARBOHYDRATE_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_CARBOHYDRATE_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_CHOLESTEROL_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_CHOLESTEROL_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_CHOLINE_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_CHOLINE_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_COPPER_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_COPPER_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_FAT_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_FAT_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_FAT_SATURATED_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_FAT_SATURATED_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_FIBER_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_FIBER_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_FLUORIDE_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_FLUORIDE_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_FOLATE_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_FOLATE_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_FOLIC_ACID_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_FOLIC_ACID_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_IODINE_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_IODINE_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_IRON_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_IRON_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_MAGNESIUM_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_MAGNESIUM_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_MANGANESE_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_MANGANESE_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_PHOSPHORUS_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_PHOSPHORUS_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_POTASSIUM_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_POTASSIUM_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_PROTEIN_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_PROTEIN_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_SELENIUM_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_SELENIUM_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_SODIUM_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_SODIUM_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_SUGAR_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_SUGAR_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_A_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_A_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_B01_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_B01_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_B02_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_B02_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_B03_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_B03_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_B05_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_B05_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_B06_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_B06_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_B12_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_B12_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_C_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_C_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_D_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_D_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_E_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_E_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_K_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_VITAMIN_K_MIN;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_ZINC_MAX;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_MEASURE_ZINC_MIN;

public class SearchNutritionFragment extends Fragment {
    private static final int MEASURE_ALCOHOL           =  0;
    private static final int MEASURE_CAFFEINE          =  1;
    private static final int MEASURE_CALCIUM           =  2;
    private static final int MEASURE_CALORIES          =  3;
    private static final int MEASURE_CARBOHYDRATES     =  4;
    private static final int MEASURE_CHOLESTEROL       =  5;
    private static final int MEASURE_CHOLINE           =  6;
    private static final int MEASURE_COPPER            =  7;
    private static final int MEASURE_FAT               =  8;
    private static final int MEASURE_FIBER             =  9;
    private static final int MEASURE_FLUORIDE          = 10;
    private static final int MEASURE_FOLATE            = 11;
    private static final int MEASURE_FOLIC_ACID        = 12;
    private static final int MEASURE_IODINE            = 13;
    private static final int MEASURE_IRON              = 14;
    private static final int MEASURE_MAGNESIUM         = 15;
    private static final int MEASURE_MANGANESE         = 16;
    private static final int MEASURE_PHOSPHOROUS       = 17;
    private static final int MEASURE_POTASSIUM         = 18;
    private static final int MEASURE_PROTEIN           = 19;
    private static final int MEASURE_SATURATED_FAT     = 20;
    private static final int MEASURE_SELENIUM          = 21;
    private static final int MEASURE_SODIUM            = 22;
    private static final int MEASURE_SUGAR             = 23;
    private static final int MEASURE_VITAMIN_A         = 24;
    private static final int MEASURE_VITAMIN_B01       = 25;
    private static final int MEASURE_VITAMIN_B02       = 26;
    private static final int MEASURE_VITAMIN_B03       = 27;
    private static final int MEASURE_VITAMIN_B05       = 28;
    private static final int MEASURE_VITAMIN_B06       = 29;
    private static final int MEASURE_VITAMIN_B12       = 30;
    private static final int MEASURE_VITAMIN_C         = 31;
    private static final int MEASURE_VITAMIN_D         = 32;
    private static final int MEASURE_VITAMIN_E         = 33;
    private static final int MEASURE_VITAMIN_K         = 34;
    private static final int MEASURE_ZINC              = 35;

    private static final int MEASURE_INDEX_FIRST = MEASURE_ALCOHOL;
    private static final int MEASURE_INDEX_LAST = MEASURE_ZINC;

    private ControlMinMax mAlcohol;
    private ControlMinMax mCaffeine;
    private ControlMinMax mCalcium;
    private ControlMinMax mCalories;
    private ControlMinMax mCarbohydrates;
    private ControlMinMax mCholesterol;
    private ControlMinMax mCholine;
    private ControlMinMax mCopper;
    private ControlMinMax mFat;
    private ControlMinMax mFiber;
    private ControlMinMax mFluoride;
    private ControlMinMax mFolate;
    private ControlMinMax mFolicAcid;
    private ControlMinMax mIodine;
    private ControlMinMax mIron;
    private ControlMinMax mMagnesium;
    private ControlMinMax mManganese;
    private ControlMinMax mPhosphorous;
    private ControlMinMax mPotassium;
    private ControlMinMax mProtein;
    private ControlMinMax mSaturatedFat;
    private ControlMinMax mSelenium;
    private ControlMinMax mSodium;
    private ControlMinMax mSugar;
    private ControlMinMax mVitaminA;
    private ControlMinMax mVitaminB01;
    private ControlMinMax mVitaminB02;
    private ControlMinMax mVitaminB03;
    private ControlMinMax mVitaminB05;
    private ControlMinMax mVitaminB06;
    private ControlMinMax mVitaminB12;
    private ControlMinMax mVitaminC;
    private ControlMinMax mVitaminD;
    private ControlMinMax mVitaminE;
    private ControlMinMax mVitaminK;
    private ControlMinMax mZinc;

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
                onControlClick(MEASURE_ALCOHOL);
            }
        });
        mCaffeine = rootView.findViewById(R.id.mm_caffeine);
        mCaffeine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_CAFFEINE);
            }
        });
        mCalcium = rootView.findViewById(R.id.mm_calcium);
        mCalcium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_CALCIUM);
            }
        });
        mCalories = rootView.findViewById(R.id.mm_calories);
        mCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_CALORIES);
            }
        });
        mCarbohydrates = rootView.findViewById(R.id.mm_carbohydrates);
        mCarbohydrates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_CARBOHYDRATES);
            }
        });
        mCholesterol = rootView.findViewById(R.id.mm_cholesterol);
        mCholesterol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_CHOLESTEROL);
            }
        });
        mCholine = rootView.findViewById(R.id.mm_choline);
        mCholine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_CHOLINE);
            }
        });
        mCopper = rootView.findViewById(R.id.mm_copper);
        mCopper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_COPPER);
            }
        });
        mFat = rootView.findViewById(R.id.mm_fat);
        mFat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_FAT);
            }
        });
        mFiber = rootView.findViewById(R.id.mm_fiber);
        mFiber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_FIBER);
            }
        });
        mFluoride = rootView.findViewById(R.id.mm_fluoride);
        mFluoride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_FLUORIDE);
            }
        });
        mFolate = rootView.findViewById(R.id.mm_folate);
        mFolate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_FOLATE);
            }
        });
        mFolicAcid = rootView.findViewById(R.id.mm_folic_acid);
        mFolicAcid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_FOLIC_ACID);
            }
        });
        mIodine = rootView.findViewById(R.id.mm_iodine);
        mIodine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_IODINE);
            }
        });
        mIron = rootView.findViewById(R.id.mm_iron);
        mIron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_IRON);
            }
        });
        mMagnesium = rootView.findViewById(R.id.mm_magnesium);
        mMagnesium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_MAGNESIUM);
            }
        });
        mManganese = rootView.findViewById(R.id.mm_manganese);
        mManganese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_MANGANESE);
            }
        });
        mPhosphorous = rootView.findViewById(R.id.mm_phosphorous);
        mPhosphorous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_PHOSPHOROUS);
            }
        });
        mPotassium = rootView.findViewById(R.id.mm_potassium);
        mPotassium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_POTASSIUM);
            }
        });
        mProtein = rootView.findViewById(R.id.mm_protein);
        mProtein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_PROTEIN);
            }
        });
        mSaturatedFat = rootView.findViewById(R.id.mm_saturated_fat);
        mSaturatedFat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_SATURATED_FAT);
            }
        });
        mSelenium = rootView.findViewById(R.id.mm_selenium);
        mSelenium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_SELENIUM);
            }
        });
        mSodium = rootView.findViewById(R.id.mm_sodium);
        mSodium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_SODIUM);
            }
        });
        mSugar = rootView.findViewById(R.id.mm_sugar);
        mSugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_SUGAR);
            }
        });
        mVitaminA = rootView.findViewById(R.id.mm_vitamin_a);
        mVitaminA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_VITAMIN_A);
            }
        });
        mVitaminB01 = rootView.findViewById(R.id.mm_vitamin_b01);
        mVitaminB01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_VITAMIN_B01);
            }
        });
        mVitaminB02 = rootView.findViewById(R.id.mm_vitamin_b02);
        mVitaminB02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_VITAMIN_B02);
            }
        });
        mVitaminB03 = rootView.findViewById(R.id.mm_vitamin_b03);
        mVitaminB03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_VITAMIN_B03);
            }
        });
        mVitaminB05 = rootView.findViewById(R.id.mm_vitamin_b05);
        mVitaminB05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_VITAMIN_B05);
            }
        });
        mVitaminB06 = rootView.findViewById(R.id.mm_vitamin_b06);
        mVitaminB06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_VITAMIN_B06);
            }
        });
        mVitaminB12 = rootView.findViewById(R.id.mm_vitamin_b12);
        mVitaminB12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_VITAMIN_B12);
            }
        });
        mVitaminC = rootView.findViewById(R.id.mm_vitamin_c);
        mVitaminC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_VITAMIN_C);
            }
        });
        mVitaminD = rootView.findViewById(R.id.mm_vitamin_d);
        mVitaminD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_VITAMIN_D);
            }
        });
        mVitaminE = rootView.findViewById(R.id.mm_vitamin_e);
        mVitaminE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_VITAMIN_E);
            }
        });
        mVitaminK = rootView.findViewById(R.id.mm_vitamin_k);
        mVitaminK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_VITAMIN_K);
            }
        });
        mZinc = rootView.findViewById(R.id.mm_zinc);
        mZinc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlClick(MEASURE_ZINC);
            }
        });

        return rootView;
    }

    private DialogFragment getDialog(int type) {
        DialogFragment dialog;
        switch (type) {
            default:
                dialog = null;
                break;
            case MEASURE_ALCOHOL:
                dialog = mAlcohol.getDialog();
                break;
            case MEASURE_CAFFEINE:
                dialog = mCaffeine.getDialog();
                break;
            case MEASURE_CALCIUM:
                dialog = mCalcium.getDialog();
                break;
            case MEASURE_CALORIES:
                dialog = mCalories.getDialog();
                break;
            case MEASURE_CARBOHYDRATES:
                dialog = mCarbohydrates.getDialog();
                break;
            case MEASURE_CHOLESTEROL:
                dialog = mCholesterol.getDialog();
                break;
            case MEASURE_CHOLINE:
                dialog = mCholine.getDialog();
                break;
            case MEASURE_COPPER:
                dialog = mCopper.getDialog();
                break;
            case MEASURE_FAT:
                dialog = mFat.getDialog();
                break;
            case MEASURE_FIBER:
                dialog = mFiber.getDialog();
                break;
            case MEASURE_FLUORIDE:
                dialog = mFluoride.getDialog();
                break;
            case MEASURE_FOLATE:
                dialog = mFolate.getDialog();
                break;
            case MEASURE_FOLIC_ACID:
                dialog = mFolicAcid.getDialog();
                break;
            case MEASURE_IODINE:
                dialog = mIodine.getDialog();
                break;
            case MEASURE_IRON:
                dialog = mIron.getDialog();
                break;
            case MEASURE_MAGNESIUM:
                dialog = mMagnesium.getDialog();
                break;
            case MEASURE_MANGANESE:
                dialog = mManganese.getDialog();
                break;
            case MEASURE_PHOSPHOROUS:
                dialog = mPhosphorous.getDialog();
                break;
            case MEASURE_POTASSIUM:
                dialog = mPotassium.getDialog();
                break;
            case MEASURE_PROTEIN:
                dialog = mProtein.getDialog();
                break;
            case MEASURE_SATURATED_FAT:
                dialog = mSaturatedFat.getDialog();
                break;
            case MEASURE_SELENIUM:
                dialog = mSelenium.getDialog();
                break;
            case MEASURE_SODIUM:
                dialog = mSodium.getDialog();
                break;
            case MEASURE_SUGAR:
                dialog = mSugar.getDialog();
                break;
            case MEASURE_VITAMIN_A:
                dialog = mVitaminA.getDialog();
                break;
            case MEASURE_VITAMIN_B01:
                dialog = mVitaminB01.getDialog();
                break;
            case MEASURE_VITAMIN_B02:
                dialog = mVitaminB02.getDialog();
                break;
            case MEASURE_VITAMIN_B03:
                dialog = mVitaminB03.getDialog();
                break;
            case MEASURE_VITAMIN_B05:
                dialog = mVitaminB05.getDialog();
                break;
            case MEASURE_VITAMIN_B06:
                dialog = mVitaminB06.getDialog();
                break;
            case MEASURE_VITAMIN_B12:
                dialog = mVitaminB12.getDialog();
                break;
            case MEASURE_VITAMIN_C:
                dialog = mVitaminC.getDialog();
                break;
            case MEASURE_VITAMIN_D:
                dialog = mVitaminD.getDialog();
                break;
            case MEASURE_VITAMIN_E:
                dialog = mVitaminE.getDialog();
                break;
            case MEASURE_VITAMIN_K:
                dialog = mVitaminK.getDialog();
                break;
            case MEASURE_ZINC:
                dialog = mZinc.getDialog();
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
        for (int i = MEASURE_INDEX_FIRST; i <= MEASURE_INDEX_LAST; i++) {
            getSingleQuery(searchTerms, i);
        }
    }

    private void getSingleQuery(@NonNull Map<String, String> searchTerms, int type) {
        DialogMinMax dialog = (DialogMinMax) getDialog(type);
        if (dialog != null) {
            String stringMinimum = dialog.getMinimum();
            int integerMinimum = stringMinimum.isEmpty() ? 0 : Integer.parseInt(stringMinimum);
            if (integerMinimum > 0) {
                searchTerms.put(getMinimumTerm(type), Integer.toString(integerMinimum));
            }
            String stringMaximum = dialog.getMaximum();
            if (!stringMaximum.isEmpty()) {
                int integerMaximum = Integer.parseInt(stringMaximum);
                searchTerms.put(getMaximumTerm(type), Integer.toString(integerMaximum));
            }
        }
    }

    private String getMinimumTerm(int type) {
        String term;
        switch (type) {
            default:
                term = null;
                break;
            case MEASURE_ALCOHOL:
                term = QUERY_COMPLEX_MEASURE_ALCOHOL_MIN;
                break;
            case MEASURE_CAFFEINE:
                term = QUERY_COMPLEX_MEASURE_CAFFEINE_MIN;
                break;
            case MEASURE_CALCIUM:
                term = QUERY_COMPLEX_MEASURE_CALCIUM_MIN;
                break;
            case MEASURE_CALORIES:
                term = QUERY_COMPLEX_MEASURE_CALORY_MIN;
                break;
            case MEASURE_CARBOHYDRATES:
                term = QUERY_COMPLEX_MEASURE_CARBOHYDRATE_MIN;
                break;
            case MEASURE_CHOLESTEROL:
                term = QUERY_COMPLEX_MEASURE_CHOLESTEROL_MIN;
                break;
            case MEASURE_CHOLINE:
                term = QUERY_COMPLEX_MEASURE_CHOLINE_MIN;
                break;
            case MEASURE_COPPER:
                term = QUERY_COMPLEX_MEASURE_COPPER_MIN;
                break;
            case MEASURE_FAT:
                term = QUERY_COMPLEX_MEASURE_FAT_MIN;
                break;
            case MEASURE_FIBER:
                term = QUERY_COMPLEX_MEASURE_FIBER_MIN;
                break;
            case MEASURE_FLUORIDE:
                term = QUERY_COMPLEX_MEASURE_FLUORIDE_MIN;
                break;
            case MEASURE_FOLATE:
                term = QUERY_COMPLEX_MEASURE_FOLATE_MIN;
                break;
            case MEASURE_FOLIC_ACID:
                term = QUERY_COMPLEX_MEASURE_FOLIC_ACID_MIN;
                break;
            case MEASURE_IODINE:
                term = QUERY_COMPLEX_MEASURE_IODINE_MIN;
                break;
            case MEASURE_IRON:
                term = QUERY_COMPLEX_MEASURE_IRON_MIN;
                break;
            case MEASURE_MAGNESIUM:
                term = QUERY_COMPLEX_MEASURE_MAGNESIUM_MIN;
                break;
            case MEASURE_MANGANESE:
                term = QUERY_COMPLEX_MEASURE_MANGANESE_MIN;
                break;
            case MEASURE_PHOSPHOROUS:
                term = QUERY_COMPLEX_MEASURE_PHOSPHORUS_MIN;
                break;
            case MEASURE_POTASSIUM:
                term = QUERY_COMPLEX_MEASURE_POTASSIUM_MIN;
                break;
            case MEASURE_PROTEIN:
                term = QUERY_COMPLEX_MEASURE_PROTEIN_MIN;
                break;
            case MEASURE_SATURATED_FAT:
                term = QUERY_COMPLEX_MEASURE_FAT_SATURATED_MIN;
                break;
            case MEASURE_SELENIUM:
                term = QUERY_COMPLEX_MEASURE_SELENIUM_MIN;
                break;
            case MEASURE_SODIUM:
                term = QUERY_COMPLEX_MEASURE_SODIUM_MIN;
                break;
            case MEASURE_SUGAR:
                term = QUERY_COMPLEX_MEASURE_SUGAR_MIN;
                break;
            case MEASURE_VITAMIN_A:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_A_MIN;
                break;
            case MEASURE_VITAMIN_B01:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_B01_MIN;
                break;
            case MEASURE_VITAMIN_B02:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_B02_MIN;
                break;
            case MEASURE_VITAMIN_B03:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_B03_MIN;
                break;
            case MEASURE_VITAMIN_B05:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_B05_MIN;
                break;
            case MEASURE_VITAMIN_B06:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_B06_MIN;
                break;
            case MEASURE_VITAMIN_B12:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_B12_MIN;
                break;
            case MEASURE_VITAMIN_C:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_C_MIN;
                break;
            case MEASURE_VITAMIN_D:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_D_MIN;
                break;
            case MEASURE_VITAMIN_E:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_E_MIN;
                break;
            case MEASURE_VITAMIN_K:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_K_MIN;
                break;
            case MEASURE_ZINC:
                term = QUERY_COMPLEX_MEASURE_ZINC_MIN;
                break;
        }
        return term;
    }

    private String getMaximumTerm(int type) {
        String term;
        switch (type) {
            default:
                term = null;
                break;
            case MEASURE_ALCOHOL:
                term = QUERY_COMPLEX_MEASURE_ALCOHOL_MAX;
                break;
            case MEASURE_CAFFEINE:
                term = QUERY_COMPLEX_MEASURE_CAFFEINE_MAX;
                break;
            case MEASURE_CALCIUM:
                term = QUERY_COMPLEX_MEASURE_CALCIUM_MAX;
                break;
            case MEASURE_CALORIES:
                term = QUERY_COMPLEX_MEASURE_CALORY_MAX;
                break;
            case MEASURE_CARBOHYDRATES:
                term = QUERY_COMPLEX_MEASURE_CARBOHYDRATE_MAX;
                break;
            case MEASURE_CHOLESTEROL:
                term = QUERY_COMPLEX_MEASURE_CHOLESTEROL_MAX;
                break;
            case MEASURE_CHOLINE:
                term = QUERY_COMPLEX_MEASURE_CHOLINE_MAX;
                break;
            case MEASURE_COPPER:
                term = QUERY_COMPLEX_MEASURE_COPPER_MAX;
                break;
            case MEASURE_FAT:
                term = QUERY_COMPLEX_MEASURE_FAT_MAX;
                break;
            case MEASURE_FIBER:
                term = QUERY_COMPLEX_MEASURE_FIBER_MAX;
                break;
            case MEASURE_FLUORIDE:
                term = QUERY_COMPLEX_MEASURE_FLUORIDE_MAX;
                break;
            case MEASURE_FOLATE:
                term = QUERY_COMPLEX_MEASURE_FOLATE_MAX;
                break;
            case MEASURE_FOLIC_ACID:
                term = QUERY_COMPLEX_MEASURE_FOLIC_ACID_MAX;
                break;
            case MEASURE_IODINE:
                term = QUERY_COMPLEX_MEASURE_IODINE_MAX;
                break;
            case MEASURE_IRON:
                term = QUERY_COMPLEX_MEASURE_IRON_MAX;
                break;
            case MEASURE_MAGNESIUM:
                term = QUERY_COMPLEX_MEASURE_MAGNESIUM_MAX;
                break;
            case MEASURE_MANGANESE:
                term = QUERY_COMPLEX_MEASURE_MANGANESE_MAX;
                break;
            case MEASURE_PHOSPHOROUS:
                term = QUERY_COMPLEX_MEASURE_PHOSPHORUS_MAX;
                break;
            case MEASURE_POTASSIUM:
                term = QUERY_COMPLEX_MEASURE_POTASSIUM_MAX;
                break;
            case MEASURE_PROTEIN:
                term = QUERY_COMPLEX_MEASURE_PROTEIN_MAX;
                break;
            case MEASURE_SATURATED_FAT:
                term = QUERY_COMPLEX_MEASURE_FAT_SATURATED_MAX;
                break;
            case MEASURE_SELENIUM:
                term = QUERY_COMPLEX_MEASURE_SELENIUM_MAX;
                break;
            case MEASURE_SODIUM:
                term = QUERY_COMPLEX_MEASURE_SODIUM_MAX;
                break;
            case MEASURE_SUGAR:
                term = QUERY_COMPLEX_MEASURE_SUGAR_MAX;
                break;
            case MEASURE_VITAMIN_A:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_A_MAX;
                break;
            case MEASURE_VITAMIN_B01:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_B01_MAX;
                break;
            case MEASURE_VITAMIN_B02:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_B02_MAX;
                break;
            case MEASURE_VITAMIN_B03:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_B03_MAX;
                break;
            case MEASURE_VITAMIN_B05:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_B05_MAX;
                break;
            case MEASURE_VITAMIN_B06:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_B06_MAX;
                break;
            case MEASURE_VITAMIN_B12:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_B12_MAX;
                break;
            case MEASURE_VITAMIN_C:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_C_MAX;
                break;
            case MEASURE_VITAMIN_D:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_D_MAX;
                break;
            case MEASURE_VITAMIN_E:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_E_MAX;
                break;
            case MEASURE_VITAMIN_K:
                term = QUERY_COMPLEX_MEASURE_VITAMIN_K_MAX;
                break;
            case MEASURE_ZINC:
                term = QUERY_COMPLEX_MEASURE_ZINC_MAX;
                break;
        }
        return term;
    }
}
