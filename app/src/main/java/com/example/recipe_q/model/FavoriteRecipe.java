package com.example.recipe_q.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class FavoriteRecipe implements Parcelable {
    private long mIdSpoonacular;
    private String mRecipeTitle;
    private String mImage;
    private long mFavoriteTime;

    public FavoriteRecipe(
            long id,
            String title,
            String image
    ) {
        this(
                id,
                title,
                image,
                Calendar.getInstance().getTimeInMillis()
        );
    }

    public FavoriteRecipe(
            long idSpoonacular,
            String recipeTitle,
            String image,
            long favoriteTime
    ) {
        mIdSpoonacular = idSpoonacular;
        mRecipeTitle = recipeTitle;
        mImage = image;
        mFavoriteTime = favoriteTime;
    }

    public long getIdSpoonacular() { return mIdSpoonacular; }
    public void setIdSpoonacular(long id) { this.mIdSpoonacular = id; }

    public String getRecipeTitle() { return mRecipeTitle; }
    public void setRecipeTitle(String title) { this.mRecipeTitle = title; }

    public String getImage() { return mImage; }
    public void setImage(String image) { this.mImage = image; }

    public long getFavoriteTime() { return mFavoriteTime; }
    public void setFavoriteTime(long favoriteTime) { this.mFavoriteTime = favoriteTime; }

    public static final Parcelable.Creator<FavoriteRecipe> CREATOR = new Parcelable.Creator<FavoriteRecipe>() {
        @Override
        public FavoriteRecipe createFromParcel(Parcel source) {
            return new FavoriteRecipe(source);
        }

        @Override
        public FavoriteRecipe [] newArray(int size) {
            return new FavoriteRecipe[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mIdSpoonacular);
        dest.writeString(mImage);
        dest.writeString(mRecipeTitle);
        dest.writeLong(mFavoriteTime);
    }

    private FavoriteRecipe(Parcel parcel) {
        setIdSpoonacular(parcel.readLong());
        setImage(parcel.readString());
        setRecipeTitle(parcel.readString());
        setFavoriteTime(parcel.readLong());
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
