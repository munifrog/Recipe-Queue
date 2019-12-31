package com.example.recipe_q.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import static com.example.recipe_q.model.IngredientConverter.convertToIngredientArrayList;

public class Recipe implements Parcelable {
    private long mIdSpoonacular;
    private String mImage;
    private String mSourceUrl;
    private String mSourceUrlSpoonacular;
    private int mReadyInMinutes;
    private float mServings;
    private String mRecipeTitle;
    private ArrayList<Ingredient> mIngredients;

    public Recipe(
            long id,
            String sourceUrl,
            String spoonacularSourceUrl,
            String image,
            int readyInMinutes,
            float servings,
            String title,
            ArrayList<Ingredient> ingredients
    ) {
        mIdSpoonacular = id;
        mSourceUrl = sourceUrl;
        mSourceUrlSpoonacular = spoonacularSourceUrl;
        mImage = image;
        mReadyInMinutes = readyInMinutes;
        mServings = servings;
        mRecipeTitle = title;
        mIngredients = ingredients;
    }

    public long getIdSpoonacular() { return mIdSpoonacular; }
    public void setIdSpoonacular(long idSpoonacular) { this.mIdSpoonacular = idSpoonacular; }

    public String getImage() { return mImage; }
    public void setImage(String image) { this.mImage = image; }

    public String getSourceUrl() { return mSourceUrl; }
    public void setSourceUrl(String sourceUrl) { this.mSourceUrl = sourceUrl; }

    public String getSourceUrlSpoonacular() { return mSourceUrlSpoonacular; }
    public void setSourceUrlSpoonacular(String sourceUrlSpoonacular) { this.mSourceUrlSpoonacular = sourceUrlSpoonacular; }

    public int getReadyInMinutes() { return mReadyInMinutes; }
    public void setReadyInMinutes(int readyInMinutes) { this.mReadyInMinutes = readyInMinutes; }

    public float getServings() { return mServings; }
    public void setServings(float servings) { this.mServings = servings; }

    public String getRecipeTitle() { return mRecipeTitle; }
    public void setRecipeTitle(String title) { this.mRecipeTitle = title; }

    public ArrayList<Ingredient> getIngredients() { return mIngredients; }
    public void setIngredients(ArrayList<Ingredient> ingredients) { this.mIngredients = ingredients; }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe [] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mIdSpoonacular);
        dest.writeString(mImage);
        dest.writeString(mSourceUrl);
        dest.writeString(mSourceUrlSpoonacular);
        dest.writeInt(mReadyInMinutes);
        dest.writeFloat(mServings);
        dest.writeString(mRecipeTitle);
        dest.writeString(IngredientConverter.convertToString(mIngredients));
    }

    private Recipe(Parcel parcel) {
        setIdSpoonacular(parcel.readLong());
        setImage(parcel.readString());
        setSourceUrl(parcel.readString());
        setSourceUrlSpoonacular(parcel.readString());
        setReadyInMinutes(parcel.readInt());
        setServings(parcel.readFloat());
        setRecipeTitle(parcel.readString());
        setIngredients(convertToIngredientArrayList(parcel.readString()));
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
