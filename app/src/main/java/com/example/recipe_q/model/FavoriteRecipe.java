package com.example.recipe_q.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Calendar;

@Entity(tableName = "favorites")
public class FavoriteRecipe implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "id_api")
    private long mIdSpoonacular;

    @ColumnInfo(name = "title")
    private String mRecipeTitle;

    @ColumnInfo(name = "image")
    private String mImage;

    @ColumnInfo(name = "save_time")
    private long mFavoriteTime;

    @Ignore
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

    @Ignore
    public Recipe asFullRecipe() {
        return new Recipe(
                mIdSpoonacular,
                null,
                null,
                mImage,
                0,
                0,
                mRecipeTitle,
                new ArrayList<Ingredient>(),
                new ArrayList<DirectionGroup>(),
                mFavoriteTime
        );
    }

    @Ignore
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

    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mIdSpoonacular);
        dest.writeString(mImage);
        dest.writeString(mRecipeTitle);
        dest.writeLong(mFavoriteTime);
    }

    @Ignore
    private FavoriteRecipe(Parcel parcel) {
        setIdSpoonacular(parcel.readLong());
        setImage(parcel.readString());
        setRecipeTitle(parcel.readString());
        setFavoriteTime(parcel.readLong());
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }
}
